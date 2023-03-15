package org.coastline.one.spark.scala.core

import org.apache.spark.launcher.JavaModuleOptions

/**
 *
 * @author Jay.H.Zou
 * @date 2023/3/4
 */
object SparkSupportJava17 {

  def main(args: Array[String]): Unit = {
    println(System.getProperty("java.version"))
    println(scala.util.Properties.releaseVersion)
    println(JavaModuleOptions.defaultModuleOptions())
  }

}
