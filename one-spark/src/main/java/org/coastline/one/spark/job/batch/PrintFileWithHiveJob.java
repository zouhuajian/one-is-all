package org.coastline.one.spark.job.batch;

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.types.DataTypes.*;


/**
 * hive 读取 csv 文件
 *
 * @author Jay.H.Zou
 * @date 2023/1/15
 */
public class PrintFileWithHiveJob {

    public static void main(String[] args) throws Exception {

        String srcPath = "/data/dataset/tmall_order_report.csv";

        SparkSession spark = SparkSession
                .builder()
                .appName("hive-read")
                .enableHiveSupport()
                .master("local[2]")
                //.master("spark://xxx:7077")
                .getOrCreate();


    }
}
