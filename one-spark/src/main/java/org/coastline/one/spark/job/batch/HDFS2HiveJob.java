package org.coastline.one.spark.job.batch;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;


/**
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

        Dataset<Row> sourceDataSet = spark.read()
                .option("header", "true")
                .csv(srcPath);
        Dataset<Row> rowDataset = sourceDataSet.toDF("order_id", "total_amount", "actual_amount", "address", "creation_time", "payment_time", "refund_amount");
        // rowDataset.show(Integer.MAX_VALUE);
        rowDataset.write()
                .format("hive")
                .mode(SaveMode.Overwrite)
                .saveAsTable(destTbl);
    }
}
