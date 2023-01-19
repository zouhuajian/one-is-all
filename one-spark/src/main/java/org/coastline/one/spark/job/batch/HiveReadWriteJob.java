package org.coastline.one.spark.job.batch;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.types.DataTypes.*;


/**
 * hive to hive 多种场景的读写操作
 *
 * @author Jay.H.Zou
 * @date 2023/1/15
 */
public class HiveReadWriteJob {
    private static final String srcPath = "bigdata.tmall_order_report_tbl";
    private static final String destPartitionTbl = "bigdata.tmall_order_report_partition_tbl";

    public static void main(String[] args) throws Exception {
        // System.setProperty("HADOOP_USER_NAME", "root");
        SparkSession spark = SparkSession
                .builder()
                .appName("hdfs-to-hive")
                .enableHiveSupport()
                //.config("hive.exec.dynamic.partition", true)
                .config("hive.exec.dynamic.partition.mode", "nonstrict")
                .master("local[2]")
                //.master("spark://xxx:7077")
                .getOrCreate();
        aggregateByAddress(spark);
    }

    /**
     * read hive
     * @param spark
     */
    private static void readHive(SparkSession spark) {

        StructType schema = new StructType()
                .add("order_id", StringType)
                .add("total_amount", DoubleType)
                .add("actual_amount", DoubleType)
                .add("address", StringType, true)
                .add("creation_time", TimestampType)
                .add("payment_time", TimestampType)
                .add("refund_amount", DoubleType);

        spark.read()
                .schema(schema)
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
     * @param spark
     */
    private static void partitionByDate(SparkSession spark) {
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
     * hive 聚合后写入另一张表 hive(parquet) 表
     * @param spark
     */
    private static void aggregateByAddress(SparkSession spark) {
        // "order_id", "total_amount", "actual_amount", "address", "creation_time", "payment_time", "refund_amount"
        StructType schema = spark.sql(String.format("SELECT address, " +
                        "sum(total_amount) AS total_amount_sum, " +
                        "sum(actual_amount) AS actual_amount_sum," +
                        "sum(payment_time) AS payment_time_sum," +
                        "sum(refund_amount) AS refund_amount_sum " +
                        "FROM %s " +
                        "GROUP BY address", srcPath))
                .schema();
        System.out.println(schema);
               /* .sort(new Column("total_amount_sum").desc())
                .show(10);*/
                /*.write()
                .partitionBy("creation_date")
                .format("hive")
                .mode(SaveMode.Overwrite)
                .saveAsTable(destTbl);*/
    }
}
