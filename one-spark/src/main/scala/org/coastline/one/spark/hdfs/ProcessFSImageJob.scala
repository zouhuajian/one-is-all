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
object ProcessFSImageJob {

  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val path: String = "file:///Users/zouhuajian/data/projects/jay/one-is-all/one-data/fsimage/fsimage.csv"

    val spark: SparkSession = core.SparkExecutor.getSparkSessionLocal("process-fsimage")

    val csv: Dataset[Row] = spark.read
      .option("header", "true")
      .option("delimiter", "\t")
      .csv(path)
    /*JavaRDD<Row> rowJavaRDD = csv.javaRDD();
            int numPartitions = rowJavaRDD.getNumPartitions();
            System.out.println(numPartitions);*/
    csv.show(Integer.MAX_VALUE, truncate = false)
  }
}
