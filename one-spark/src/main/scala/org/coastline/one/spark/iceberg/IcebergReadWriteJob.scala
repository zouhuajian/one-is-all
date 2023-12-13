package org.coastline.one.spark.iceberg

import org.apache.iceberg.exceptions.NoSuchTableException
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}
import org.coastline.one.core.tool.TimeTool

import java.util.concurrent.TimeUnit

/**
 *
 * @author Jay.H.Zou
 * @date 2023/2/12
 */
object IcebergReadWriteJob {

  @throws[NoSuchTableException]
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder.appName("IcebergReaderWriter")
      .config("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
      // catalog: iceberg_catalog
      .config("spark.sql.catalog.iceberg_catalog", "org.apache.iceberg.spark.SparkCatalog")
      // hadoop
      .config("spark.sql.catalog.iceberg_catalog.type", "hadoop")
      .config("spark.sql.catalog.iceberg_catalog.default-namespace", "bigdata_iceberg")
      .config("spark.sql.catalog.iceberg_catalog.warehouse", "/data/warehouse/bigdata_iceberg")
      // hive
      /*.config("spark.sql.catalog.iceberg_catalog.type", "hive")
      .config("spark.sql.catalog.iceberg_catalog.default-namespace", "bigdata_iceberg")
      .config("spark.sql.catalog.iceberg_catalog.uri", "thrift://metastore-host:port")*/
      .config("spark.sql.catalog.iceberg_catalog.cache-enabled", "false")
      .master("local[*]")
      .getOrCreate

    // db.table should be headed by "iceberg_catalog"
    val table = "iceberg_catalog.bigdata_iceberg.iceberg_table_1"

    readIceberg(spark, table)
  }

  /**
   * Stream read & write
   * @param spark SparkSession
   */
  private def streamReadWrite(spark: SparkSession, srcTable: String, dstTable: String): Unit = {
    val sourceStream = spark.readStream
      .format("iceberg")
      .option("stream-from-timestamp", TimeTool.currentTimeMillis().toString)
      .load(srcTable)

    sourceStream.show()

    sourceStream.writeStream
      .format("iceberg")
      .outputMode("append")
      .trigger(Trigger.ProcessingTime(1, TimeUnit.MINUTES))
      .option("path", dstTable)
      .option("checkpointLocation", "/data/warehouse/bigdata_iceberg/")
      .start()
  }

  /**
   * 压缩文件
   *
   * @param spark
   */
  /*private def maintenanceFiles(spark: SparkSession): Unit = {
    SparkActions.get(spark)
      .rewriteDataFiles(new SnapshotsTable())
      .filter(Expressions.equal("date", "2020-08-18"))
      .option("target-file-size-bytes", String.valueOf(500 * 1024 * 1024)) // 500 MB
      .execute();
  }*/

  private def readIceberg(spark: SparkSession, table: String): Unit = {
    println("************* history *************")
    spark.sql(s"SELECT * FROM $table.history").show(10, truncate = false)
    println("************* snapshots *************")
    spark.sql(s"SELECT * FROM $table.snapshots").show(10, truncate = false)
    println("************* files *************")
    spark.sql(s"SELECT * FROM $table.files").show(10, truncate = false)
    println("************* manifests *************")
    spark.sql(s"SELECT * FROM $table.manifests").show(10, truncate = false)
    println("************* partitions *************")
    spark.sql(s"SELECT * FROM $table.partitions").show(10, truncate = false)
  }

  private def writeIceberg(spark: SparkSession, table: String): Unit = {
    spark.sql(
      s"""
         |CREATE TABLE IF NOT EXISTS $table (
         |  id bigint,
         |  data string,
         |  ts timestamp)
         |USING iceberg
         |PARTITIONED BY (date(ts))
        """.stripMargin)

    val schema = new StructType()
      .add(StructField("id", IntegerType, nullable = true))
      .add(StructField("data", StringType, nullable = true))
      .add(StructField("ts", TimestampType, nullable = false))

    val source = Seq(
      Row(1, "a", TimeTool.currentTimestamp()),
      Row(2, "b", TimeTool.currentTimestamp()),
      Row(3, "c", TimeTool.currentTimestamp())
    )
    val sourceRdd = spark.sparkContext.parallelize(source)
    // Insert data into table1
    spark.createDataFrame(sourceRdd, schema)
      .writeTo(table)
      .append()
    spark.sql(s"SELECT * FROM $table").show(10, truncate = false)
  }
}
