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
                .config("spark.sql.warehouse.dir", "/data/warehouse/")
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
                .option("delimiter", ",")
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
/*
CREATE TABLE `tmall_order_report_tbl`
    (
    `order_id`      string,
    `total_amount`  double,
    `actual_amount` double,
    `address`       string,
    `creation_time` timestamp,
    `payment_time`  timestamp,
    `refund_amount` double
    )
    ROW FORMAT SERDE
    'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
    STORED AS INPUTFORMAT
    'org.apache.hadoop.mapred.TextInputFormat'
    OUTPUTFORMAT
    'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
    LOCATION
    'hdfs://coastline-4:9000/data/warehouse/bigdata.db/tmall_order_report_tbl'
    TBLPROPERTIES (
    'spark.sql.create.version' = '3.3.0',
    'spark.sql.sources.schema' =
    '{type:struct,fields:[{name:order_id,type:string,nullable:true,metadata:{}},{name:total_amount,type:double,nullable:true,metadata:{}},{name:actual_amount,type:double,nullable:true,metadata:{}},{name:address,type:string,nullable:true,metadata:{}},{name:creation_time,type:timestamp,nullable:true,metadata:{}},{name:payment_time,type:timestamp,nullable:true,metadata:{}},{name:refund_amount,type:double,nullable:true,metadata:{}}]}',
    'transient_lastDdlTime' = '1674054191');
*/
/*
CREATE TABLE `tmall_order_report_partition_tbl`
    (
    `order_id`      string,
    `total_amount`  string,
    `actual_amount` string,
    `address`       string,
    `creation_time` string,
    `payment_time`  string,
    `refund_amount` string
    )
    PARTITIONED BY (
    `creation_date` string)
    ROW FORMAT SERDE
    'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
    STORED AS INPUTFORMAT
    'org.apache.hadoop.mapred.TextInputFormat'
    OUTPUTFORMAT
    'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
    LOCATION
    'hdfs://coastline-4:9000/data/warehouse/bigdata.db/tmall_order_report_partition_tbl'
    TBLPROPERTIES (
    'spark.sql.create.version' = '3.3.0',
    'spark.sql.sources.schema' =
    '{type:struct,fields:[{name:order_id,type:string,nullable:true,metadata:{}},{name:total_amount,type:string,nullable:true,metadata:{}},{name:actual_amount,type:string,nullable:true,metadata:{}},{name:address,type:string,nullable:true,metadata:{}},{name:creation_time,type:string,nullable:true,metadata:{}},{name:payment_time,type:string,nullable:true,metadata:{}},{name:refund_amount,type:string,nullable:true,metadata:{}},{name:creation_date,type:string,nullable:true,metadata:{}}]}',
    'spark.sql.sources.schema.numPartCols' = '1',
    'spark.sql.sources.schema.partCol.0' = 'creation_date',
    'transient_lastDdlTime' = '1674052639');
*/
