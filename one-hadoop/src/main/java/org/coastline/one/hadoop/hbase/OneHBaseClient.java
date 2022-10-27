package org.coastline.one.hadoop.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.coastline.one.core.tool.HashTool;
import org.coastline.one.core.tool.TimeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * create 'test_table', {NAME => 'X', COMPRESSION => 'SNAPPY', TTL=>'604800', DATA_BLOCK_ENCODING => 'FAST_DIFF', BLOCKCACHE => FALSE}, {NUMREGIONS => 4, SPLITALGO => 'HexStringSplit'}
 *
 * @author Jay.H.Zou
 * @date 2022/10/14
 */
public class OneHBaseClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneHBaseClient.class);

    private static final String TABLE = "test_table";

    private static final String KEY = "key";

    private static final byte[] ROW_KEY = (HashTool.hashMurmur3_32(KEY) + "-" + KEY).getBytes(StandardCharsets.UTF_8);

    private final Connection connection;
    // private BufferedMutator bufferedMutator;
    private static final AtomicInteger COLUMN_COUNT = new AtomicInteger();
    private static final AtomicInteger RPC_COUNT = new AtomicInteger();
    private static final AtomicLong SERIALIZED_SIZE = new AtomicLong();

    public OneHBaseClient() throws IOException {
        connection = ConnectionFactory.createConnection();
        /*Configuration hbaseConfig = HBaseConfiguration.create();
        hbaseConfig.set(HConstants.ZOOKEEPER_QUORUM, "xxx");
        hbaseConfig.set(HConstants.HBASE_CLIENT_META_OPERATION_TIMEOUT, "60000");
        hbaseConfig.setLong("hbase.ipc.client.connection.minIdleTimeBeforeClose", 43200000);
        BufferedMutator.ExceptionListener listener = (e, mutator) -> {
            for (int i = 0; i < e.getNumExceptions(); i++) {
                // LOGGER.error("async sent puts error: {}", e.getRow(i));
            }
        };
        long writeBuffer = 10 * 1024 * 1024;
        BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf(table))
                .writeBufferSize(writeBuffer)
                .pool(new ThreadPoolExecutor(8, 8,
                        1, TimeUnit.HOURS,
                        new ArrayBlockingQueue<>(10),
                        new ThreadPoolExecutor.CallerRunsPolicy()))
                .listener(listener);
        bufferedMutator = connection.getBufferedMutator(params);*/
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws IOException {
        connection.close();
    }

    public void put() {
        List<Put> putList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String key = "one-" + i;
            byte[] rowKey = (HashTool.hashMurmur3_32(key) + "|" + key).getBytes(StandardCharsets.UTF_8);
            Put put = new Put(rowKey);
            for (int j = 0; j < 5; j++) {
                byte[] column = String.valueOf(j).getBytes(StandardCharsets.UTF_8);
                put.addColumn("X".getBytes(StandardCharsets.UTF_8),
                        column,
                        column);
            }
            put.setDurability(Durability.SKIP_WAL);
            putList.add(put);
        }
        try (Table table = connection.getTable(TableName.valueOf(TABLE))) {
            table.put(putList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void append() {
        String key = "one";
        byte[] rowKey = (HashTool.hashMurmur3_32(key) + "-" + key).getBytes(StandardCharsets.UTF_8);
        Put put = new Put(rowKey);
        put.addColumn("X".getBytes(StandardCharsets.UTF_8),
                "a".getBytes(StandardCharsets.UTF_8),
                "1".getBytes(StandardCharsets.UTF_8));
        put.setDurability(Durability.SKIP_WAL);

        Append append = new Append(rowKey);
        append.addColumn("X".getBytes(StandardCharsets.UTF_8),
                "a".getBytes(StandardCharsets.UTF_8),
                "2".getBytes(StandardCharsets.UTF_8));
        Get get = new Get(rowKey);
        try (Table table = connection.getTable(TableName.valueOf(TABLE))) {

            table.put(put);
            decode(table.get(get));
            table.append(append);
            decode(table.get(get));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRow() {
        long start = TimeTool.currentTimeMillis();
        Get get = new Get(ROW_KEY);
        try (Table table = connection.getTable(TableName.valueOf(TABLE))) {
            Result result = table.get(get);
            decode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("cost time = " + (TimeTool.currentTimeMillis() - start) + "ms");
    }

    public void getRowByScan() {
        long start = TimeTool.currentTimeMillis();
        Scan scan = new Scan();
        //.setMaxResultSize(128L) // 对单行没有意义
        scan.withStartRow(ROW_KEY)
                .withStopRow(ROW_KEY, true)
                //.setAllowPartialResults(true);
                .setBatch(5000);
        try (Table table = connection.getTable(TableName.valueOf(TABLE))) {
            ResultScanner scanner = table.getScanner(scan);
            for (Result r = scanner.next(); r != null; r = scanner.next()) {
                RPC_COUNT.incrementAndGet();
                decode(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("cost time={}ms", TimeTool.currentTimeMillis() - start);
    }

    private void decode(Result result) {
        List<Cell> cells = result.listCells();
        if (cells == null || cells.isEmpty()) {
            return;
        }
        LOGGER.info("cell number={}", cells.size());
        // System.out.print(new String(result.getRow()) + ",\t");
        for (Cell cell : cells) {
            COLUMN_COUNT.incrementAndGet();
            //System.out.println("family: " + new String(CellUtil.cloneFamily(cell)));
            //System.out.println("qualifier: " + new String(CellUtil.cloneQualifier(cell)));
            //System.out.print(", value: " + new String(CellUtil.cloneValue(cell)));
            SERIALIZED_SIZE.addAndGet(cell.getSerializedSize());
        }
    }

    public static void main(String[] args) throws IOException {
        OneHBaseClient oneHBaseClient = new OneHBaseClient();
        oneHBaseClient.getRowByScan();
        LOGGER.info("rpc_count={}, column_count={}, serialized_size={}MB", RPC_COUNT.get(), COLUMN_COUNT.get(), SERIALIZED_SIZE.get() / 1024 / 1024);
        oneHBaseClient.close();
    }

}
