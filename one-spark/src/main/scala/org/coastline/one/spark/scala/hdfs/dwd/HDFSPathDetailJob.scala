package org.coastline.one.spark.scala.hdfs.dwd

import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.coastline.one.spark.scala.core.SparkExecutor

/**
 *
 * @author Jay.H.Zou
 * @date 2023/2/15
 */
object HDFSPathDetailJob {

  def main(args: Array[String]): Unit = {
    val srcFile = "file:///Users/zouhuajian/data/projects/jay/one-is-all/one-data/fsimage/fsimage.csv"

    val spark = SparkExecutor.getSparkSessionLocal("hdfs_path_detail_job")
    val sourceRDD: Dataset[Row] = spark.read
      .option("header", "true")
      .option("delimiter", "\t")
      .csv(srcFile)

    sourceRDD.toDF()

    sourceRDD.show(Integer.MAX_VALUE, truncate = false)
  }
}
