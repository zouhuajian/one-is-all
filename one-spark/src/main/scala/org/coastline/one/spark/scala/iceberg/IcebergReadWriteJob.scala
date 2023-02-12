package org.coastline.one.spark.scala.iceberg

import org.apache.iceberg.exceptions.NoSuchTableException
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.coastline.one.core.tool.TimeTool

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
