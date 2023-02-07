package org.coastline.one.spark.scala

import org.coastline.one.core.tool.TimeTool

import java.time.LocalDateTime

/**
 *
 * @author Jay.H.Zou
 * @date 2023/1/31
 */
object OneScala {

  def main(args: Array[String]): Unit = {
    /*val tuple = ("LMagics", 173.5, Seq(22, 66, 88))
    println(getTableLocations("xxx"))*/
    val dateTime: LocalDateTime = TimeTool.currentLocalDateTime.plusDays(-1)
    val dt: String = dateTime.format(TimeTool.DEFAULT_DATE_FORMATTER)
    println(dt)
  }

  private def getTableLocations(cluster: String): String = {
    "a" + cluster
  }

  class Test {
    private var pCount = 0

    def count = pCount // getter

    def count_=(c: Int) = { // setter
      require(c > 0)
      pCount = c
    }
  }
}
