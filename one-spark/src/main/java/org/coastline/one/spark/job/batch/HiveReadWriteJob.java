package org.coastline.one.spark.job.batch;

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import org.coastline.one.core.tool.TimeTool;
import org.coastline.one.spark.core.model.OrderReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.spark.sql.types.DataTypes.*;

/**
 * hive to hive 多种场景的读写操作
 * <p>
 * Queries are expressed in HiveQL
 *
 * @author Jay.H.Zou
 * @date 2023/1/15
 */
public class HiveReadWriteJob {

    private static final StructType BASE_SCHEMA = new StructType()
            .add("order_id", StringType)
            .add("total_amount", DoubleType)
            .add("actual_amount", DoubleType)
            .add("address", StringType, true)
            .add("creation_time", TimestampType)
            .add("payment_time", TimestampType)
            .add("refund_amount", DoubleType);

    public static void main(String[] args) throws Exception {
        System.setProperty("HADOOP_USER_NAME", "root");
        SparkSession spark = SparkSession
                .builder()
                .appName("hdfs-to-hive")
                .enableHiveSupport()
                //.config("hive.exec.dynamic.partition", true)
                .config("hive.exec.dynamic.partition.mode", "nonstrict")
                .master("local[2]")
                //.master("spark://xxx:7077")
                .getOrCreate();
        unionAll(spark);
    }

    /**
     * read hive
     *
     * @param spark
     */
    private static void readHive(SparkSession spark) {
        String srcPath = "bigdata.tmall_order_report_tbl";

        spark.read()
                .schema(BASE_SCHEMA)
                .csv(srcPath)
                .toDF("order_id", "total_amount", "actual_amount", "address", "creation_time", "payment_time", "refund_amount")
                .where("address='上海' and actual_amount>250")
                //.select("address", "total_amount", "actual_amount", "creation_time")
                .orderBy(new Column("actual_amount").desc())
                //.coalesce(2)
                //.repartition(2)
                .show(Integer.MAX_VALUE);
    }

    /**
     * hive 写入另一张分区 hive 表
     *
     * @param spark
     */
    private static void partitionByDate(SparkSession spark) {
        String srcPath = "bigdata.tmall_order_report_tbl";
        String destPartitionTbl = "bigdata.tmall_order_report_partition_tbl";

        // "order_id", "total_amount", "actual_amount", "address", "creation_time", "payment_time", "refund_amount"
        spark.sql(String.format("SELECT order_id, " +
                        "total_amount, " +
                        "actual_amount, " +
                        "address, " +
                        "creation_time, " +
                        "date_format(creation_time, 'yyyy-MM-dd') AS creation_date, " +
                        "payment_time, " +
                        "refund_amount " +
                        "FROM %s", srcPath))
                //.show(10);
                .write()
                .partitionBy("creation_date")
                .format("hive")
                .mode(SaveMode.Overwrite)
                .saveAsTable(destPartitionTbl);
    }

    /**
     * hive 聚合后写入另一张表 hive(parquet/orc) 表
     *
     * @param spark
     */
    private static void aggregateByAddress(SparkSession spark) {
        String srcPath = "bigdata.tmall_order_report_partition_tbl";
        String destPartitionTbl = "bigdata.tmall_order_report_agg_partition_tbl";

        // "order_id", "total_amount", "actual_amount", "address", "creation_time", "payment_time", "refund_amount"
        spark.sql(String.format("SELECT address, " +
                        "sum(cast(total_amount as decimal(15,2))) AS total_amount_sum, " +
                        "sum(cast(actual_amount as decimal(15,2))) AS actual_amount_sum," +
                        "sum(cast(payment_time as decimal(15,2))) AS payment_time_sum," +
                        "sum(cast(refund_amount as decimal(15,2))) AS refund_amount_sum, " +
                        "creation_date " +
                        "FROM %s " +
                        "GROUP BY address, creation_date", srcPath))

                .sort(new Column("total_amount_sum").desc())
                .write()
                .partitionBy("creation_date")
                .mode(SaveMode.Overwrite)
                .format("orc")
                .saveAsTable(destPartitionTbl);
        // parquet(path), orc(path) 适合写入全路径
    }

    /**
     * hive 聚合后写入另一张表 hive(parquet/orc) 表
     *
     * @param spark
     */
    private static void unionAll(SparkSession spark) {
        Random random = new Random();
        String srcPath = "bigdata.tmall_order_report_agg_partition_tbl";
        String destTbl = "bigdata.tmall_order_report_agg_partition_tbl";

        List<OrderReport> orderReports = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int r = random.nextInt(10);
            orderReports.add(OrderReport.builder()
                    .orderId(String.valueOf(r))
                    .totalAmount(r)
                    .actualAmount(r)
                    .address("上海")
                    .creationTime(TimeTool.currentTimestamp())
                    .paymentTime(TimeTool.currentTimestamp())
                    .refundAmount(r)
                    .build());
        }
        Dataset<Row> inMemory = spark.createDataFrame(orderReports, OrderReport.class)
                .toDF(BASE_SCHEMA.names());
        inMemory.createOrReplaceTempView("in_memory_orders");

        Dataset<Row> origin = spark.sql(String.format("SELECT order_id, total_amount, actual_amount, address, creation_time, payment_time, refund_amount " +
                "FROM %s " +
                "WHERE address='上海' " +
                "ORDER BY order_id asc", srcPath));

        /*spark.sql("SELECT * FROM in_memory_orders m " +
                "UNION ALL bigdata.tmall_order_report_agg_partition_tbl").show();*/
        origin.unionAll(inMemory).show(Integer.MAX_VALUE);

                /*.write()
                .partitionBy("creation_date")
                .mode(SaveMode.Overwrite)
                .format("parquet")
                .saveAsTable(destTbl);*/
    }
}
