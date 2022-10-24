package org.coastline.one.hadoop.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.coastline.one.core.tool.TimeTool;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jay.H.Zou
 * @date 2022/10/21
 */
public class ScanHBase {
    private static final TableName TABLE_NAME = TableName.valueOf("tp221016");
    private static final int CORE = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE + 1, CORE + 1,
            1, TimeUnit.HOURS, new ArrayBlockingQueue<>(1550));
    private static final AtomicLong COUNT = new AtomicLong();


    public static void main(String[] args) throws Exception {

        long start = TimeTool.currentTimeMillis();
        OneHBaseClient oneHBaseClient = new OneHBaseClient();
        Connection connection = oneHBaseClient.getConnection();
        Admin admin = connection.getAdmin();
        List<RegionInfo> regions = admin.getRegions(TABLE_NAME);
        String startMsg = "start scan, region number: " + regions.size();
        System.out.println(startMsg);
        CountDownLatch countDownLatch = new CountDownLatch(regions.size());
        for (RegionInfo region : regions) {
            executor.submit(new ScanRegionTask(connection, region, countDownLatch));
        }
        countDownLatch.await();
        oneHBaseClient.close();
        String msg = TABLE_NAME.getNameAsString() + " row number: " + COUNT.get() + ", scan cost: " + (TimeTool.currentTimeMillis() - start) / 1000 + "s";
        System.out.println(msg);
        executor.shutdown();
    }

    static class ScanRegionTask implements Runnable {

        private final Connection connection;
        private final RegionInfo region;
        private final CountDownLatch countDown;

        public ScanRegionTask(Connection connection, RegionInfo region, CountDownLatch countDown) {
            this.connection = connection;
            this.region = region;
            this.countDown = countDown;
        }

        @Override
        public void run() {
            String regionName = new String(region.getRegionName());
            //String startMsg = "start scan region " + regionName;
            long start = TimeTool.currentTimeMillis();
            byte[] startKey = region.getStartKey();
            byte[] endKey = region.getEndKey();
            Scan scan = new Scan()
                    .withStartRow(startKey)
                    .withStopRow(endKey)
                    .addFamily(Bytes.toBytes("S"))
                    .setMaxResultsPerColumnFamily(1)
                    .setBatch(10000000);
            try (Table table = connection.getTable(TABLE_NAME)) {
                ResultScanner scanner = table.getScanner(scan);
                for (Result r = scanner.next(); r != null; r = scanner.next()) {
                    COUNT.incrementAndGet();
                }
            } catch (Exception e) {
                System.err.println("scan region error, region=" + region);
                e.printStackTrace();
            }
            String msg = "current number=" + COUNT.get() + ", scan region " + regionName + " finished, cost: " + (TimeTool.currentTimeMillis() - start) / 1000 + "s";
            System.out.println(msg);
            countDown.countDown();
        }
    }
}
