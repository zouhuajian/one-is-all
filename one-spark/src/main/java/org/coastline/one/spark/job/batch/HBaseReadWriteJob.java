/*
package org.coastline.one.spark.job.batch;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.spark.JavaHBaseContext;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.coastline.one.spark.core.SparkExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.nio.charset.StandardCharsets;

*/
/**
 * HBase Read Write Job
 * <p>
 *     create 'one_order', {NAME => 'X', TTL=>'604800', DATA_BLOCK_ENCODING => 'FAST_DIFF' }, {NUMREGIONS => 4, SPLITALGO => 'HexStringSplit'}
 * </p>
 * @author Jay.H.Zou
 * @date 2023/01/27
 *//*

public class HBaseReadWriteJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseReadWriteJob.class);

    private static final TableName TABLE_NAME = TableName.valueOf("one_order");

    private static final byte[] FAMILY = Bytes.toBytes("X");

    public static void main(String[] args) {
        String srcPath = "bigdata.tmall_order_report_tbl";
        SparkSession spark = SparkExecutor.getSparkSessionWithHiveLocal("hive-to-hbase");

        JavaRDD<Tuple2<String, Double>> source = spark.sql(String.format("SELECT order_id, " +
                        "total_amount " +
                        "FROM %s", srcPath))
                .javaRDD()
                .map(new Function<Row, Tuple2<String, Double>>() {
                    @Override
                    public Tuple2<String, Double> call(Row row) throws Exception {
                        return new Tuple2<>(row.getString(0), row.getDouble(1));
                    }
                });

        // 根据RowKey排序后再写入HBase, 可以增加写入性能, 减少集群负载
        JavaRDD<Tuple2<String, Double>> sortedRDD = source.sortBy(new Function<Tuple2<String, Double>, String>() {
            @Override
            public String call(Tuple2<String, Double> tuple2) throws Exception {
                return tuple2._1();
            }
        }, true, source.getNumPartitions());

        sortedRDD.foreach(new VoidFunction<Tuple2<String, Double>>() {
            @Override
            public void call(Tuple2<String, Double> tuple2) throws Exception {
                System.out.println(tuple2);
            }
        });

        // 将用于将数据转换为HBase可识别的Put类型.
        */
/*Function<Tuple2<String, Double>, Put> putFunction = (Tuple2<String, Double> tuple2) -> {
            byte[] rowKey = tuple2._1().getBytes(StandardCharsets.UTF_8);
            Put put = new Put(rowKey);
            put.addColumn(FAMILY, Bytes.toBytes("total_amount"), Bytes.toBytes(tuple2._2()));
            return put;
        };

        JavaSparkContext jsc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        // config from resources
        JavaHBaseContext hbaseContext = new JavaHBaseContext(jsc, new Configuration());

        // autoFlush(最后一个参数), 设置为false为批量写入, 能大大提高写入性能. 设置为true时, HBase写入性能极低.
        hbaseContext.bulkPut(sortedRDD, TABLE_NAME, putFunction);*//*

    }
}
*/
