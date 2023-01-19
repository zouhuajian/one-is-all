package org.coastline.one.spark.job.batch;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.types.DataTypes.*;


/**
 *
 * 从 hdfs 中读取文件写入 hive 表
 *
 * @author Jay.H.Zou
 * @date 2023/1/15
 */
public class HDFS2HiveJob {

    public static void main(String[] args) throws Exception {

        String srcPath = "/data/dataset/tmall_order_report.csv";
        String destTbl = "bigdata.tmall_order_report_tbl";

        SparkSession spark = SparkSession
                .builder()
                .appName("hdfs-to-hive")
                .enableHiveSupport()
                //.config("spark.sql.warehouse.dir", "/data/warehouse/")
                .master("local[2]")
                //.master("spark://xxx:7077")
                .getOrCreate();

        StructType schema = new StructType()
                .add("order_id", StringType)
                .add("total_amount", DoubleType)
                .add("actual_amount", DoubleType)
                .add("address", StringType, true)
                .add("creation_time", TimestampType)
                .add("payment_time", TimestampType)
                .add("refund_amount", DoubleType);

        Dataset<Row> sourceDataSet = spark.read()
                .option("header", "true")
                .schema(schema)
                .csv(srcPath)
                .cache();

        Dataset<Row> rowDataset = sourceDataSet.toDF("order_id", "total_amount", "actual_amount", "address", "creation_time", "payment_time", "refund_amount");
        rowDataset.show(Integer.MAX_VALUE);
        rowDataset.write()
                .format("hive")
                .mode(SaveMode.Overwrite)
                .saveAsTable(destTbl);
    }
}
