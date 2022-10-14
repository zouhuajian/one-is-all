package org.coastline.one.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.coastline.one.core.tool.HashTool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * create 'test_table', {NAME => 'X', COMPRESSION => 'SNAPPY', TTL=>'604800', DATA_BLOCK_ENCODING => 'FAST_DIFF', BLOCKCACHE => FALSE}, {NUMREGIONS => 4, SPLITALGO => 'HexStringSplit'}
 *
 * @author Jay.H.Zou
 * @date 2022/10/14
 */
public class OneHBaseClient {

    private static final String TABLE = "test_table";

    private final Connection connection;
    private BufferedMutator bufferedMutator;

    public OneHBaseClient() throws IOException {
        Configuration hbaseConfig = HBaseConfiguration.create();
        hbaseConfig.set(HConstants.ZOOKEEPER_QUORUM, "xxx");
        hbaseConfig.set(HConstants.HBASE_CLIENT_META_OPERATION_TIMEOUT, "60000");
        hbaseConfig.setLong("hbase.ipc.client.connection.minIdleTimeBeforeClose", 43200000);
        // ------ 在配置文件存在时，以上可不需要 ------
        connection = ConnectionFactory.createConnection();

        /*BufferedMutator.ExceptionListener listener = (e, mutator) -> {
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

    private void close() throws IOException {
        connection.close();;
    }


    public void testAppend() {
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
            decodeResult(table.get(get));
            table.append(append);
            decodeResult(table.get(get));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testGetRowQualifierNumber() {

        String key = "one";
        byte[] rowKey = (HashTool.hashMurmur3_32(key) + "-" + key).getBytes(StandardCharsets.UTF_8);

        Get get = new Get(rowKey);
        try (Table table = connection.getTable(TableName.valueOf(TABLE))) {
            decodeResultForLong(table.get(get));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(Result result) {
        List<Cell> cells = result.listCells();
        if (cells == null || cells.isEmpty()) {
            return;
        }
        byte[] row = result.getRow();
        System.out.print(new String(row));
        for (Cell cell : cells) {
            System.out.print(", family: " + new String(CellUtil.cloneFamily(cell)));
            System.out.print(", qualifier: " + new String(CellUtil.cloneQualifier(cell)));
            System.out.print(", value: " + new String(CellUtil.cloneValue(cell)));
        }
        System.out.println();
    }

    private void decodeResultForLong(Result result) throws IOException {
        byte[] row = result.getRow();
        System.out.print(new String(row));
        CellScanner cellScanner = result.cellScanner();
        while (cellScanner.advance()) {
            Cell cell = cellScanner.current();
            System.out.print(", family: " + new String(CellUtil.cloneFamily(cell)));
            System.out.print(", qualifier: " + new String(CellUtil.cloneQualifier(cell)));
            System.out.print(", value: " + new String(CellUtil.cloneValue(cell)));
        }
        System.out.println();
    }




    public static void main(String[] args) throws IOException {
        OneHBaseClient oneHBaseClient = new OneHBaseClient();
        oneHBaseClient.testGetRowQualifierNumber();
        oneHBaseClient.close();
    }

}
