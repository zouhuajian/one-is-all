package org.coastline.one.scala

import org.coastline.one.core.tool.TimeTool

import java.time.LocalDateTime
import scala.util.control.Breaks.break

/**
 *
 * @author Jay.H.Zou
 * @date 2023/4/12
 */
object OneScala {
  def main(args: Array[String]): Unit = {
    /*val tuple = ("LMagics", 173.5, Seq(22, 66, 88))
    println(getTableLocations("xxx"))*/
    /*val dateTime: LocalDateTime = TimeTool.currentLocalDateTime.plusDays(-1)
    val dt: String = dateTime.format(TimeTool.DEFAULT_DATE_FORMATTER)
    println(dt)*/
    /*val arr = Seq(1, 2, 0, 3, 4)
    arr.foreach(n => {
      try {
        println(16/n)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    })*/
    val cluster = "111"
    // println(s"${cluster}_table_meta_view")

    val path = "/data/BI_RECO_VERT"
    val tablePath = "/data"
    val newPath = path.replaceFirst(tablePath + "/", "")
    val partitionPath = tablePath + "/" + newPath.substring(0, newPath.indexOf("/"))
    println(partitionPath)
  }
}
