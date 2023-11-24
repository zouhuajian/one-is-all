package org.coastline.one.spark.scala

import org.coastline.one.spark.scala.core.SparkExecutor

object ReadFormatFileJob {

  def main(args: Array[String]): Unit = {

    val spark = SparkExecutor.getSparkSessionLocal("read_format_file")
    /*spark.read
      .format("avro")
      .load("file:///Users/zouhuajian/data/projects/jay/one-is-all/one-data/avro")
      .show(100)*/
    val date = "2023-10-19"
    spark.read
      .parquet(s"file:///Users/zouhuajian/data/projects/jay/hdfs-viewer/data/storage/dump/$date")
      //.show(100, truncate = false)
      .createOrReplaceTempView("ods_hdfs_path_view")

    spark.sql(
      s"""
         |SELECT *
         |FROM ods_hdfs_path_view
         |WHERE path = '/system'
         |""".stripMargin)
      .show(2)
  }
}
