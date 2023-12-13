package org.coastline.one.spark.hdfs

import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.coastline.one.spark.core
import org.coastline.one.spark.core.SparkExecutor

/**
 * 读取 oiv 解析后的 fsimage
 *
 * @author Jay.H.Zou
 * @date 2023/2/11
 */
object AggPathJob {

  @throws[Exception]
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = core.SparkExecutor.getSparkSessionLocal("agg-path")

    import spark.implicits._
    Seq(("/a", "/", 1), ("/a/1", "/a", 1), ("/a/2", "/a", 1), ("/a/2/1", "/a/2", 1), ("/b/1", "/b", 1), ("/b", "/", 1))
      .toDF("path", "parent_path", "size")
      .createOrReplaceTempView("hdfs_path_view")

    spark.sql(
        s"""
           |SELECT parent_path, sum(size) AS agg_size
           |FROM hdfs_path_view
           |GROUP BY parent_path
           |""".stripMargin)
      .show(100)
  }
}
