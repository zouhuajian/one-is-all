package org.coastline.one.spark

import org.apache.spark.{SparkConf, SparkContext}

object OneSpark {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("one-spark").setMaster("local")
    val sc = new SparkContext(conf)

    //val data = Array(1, 2, 3, 4, 5)

    //val data: List[List[Int]] = List(List(1, 2), List(3), List(), List(4, 5))
    val data = List(("hadoop", 3), ("hadoop", 2), ("spark", 4), ("spark", 3), ("storm", 6), ("storm", 8))
    val distData = sc.parallelize(data, 6)
    // (spark,CompactBuffer(3, 5))
    // (hadoop,CompactBuffer(2, 2))
    // (storm,CompactBuffer(6))

    // distData.groupByKey().map(x => (x._1, x._2.toList)).foreach(println)
    // distData.sortByKey(ascending = true).foreach(println)
    // distData.sortBy(x => x._2, ascending = false).foreach(println)

    distData.aggregateByKey(zeroValue = 0, numPartitions = 3)(
      seqOp = math.max,
      combOp = _ + _
    ).foreach(println)
  }
}
