package org.coastline.one.spark.core

import org.apache.spark.sql.SparkSession

/**
 *
 * @author Jay.H.Zou
 * @date 2023/2/11
 */
object SparkExecutor {

  def getSparkSession(appname: String, local: Boolean, withHive: Boolean): SparkSession = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val sparkBuilder = SparkSession.builder.appName(appname)
    if (withHive) {
      sparkBuilder.enableHiveSupport.config("hive.exec.dynamic.partition.mode", "nonstrict")
    }
    if (local) {
      sparkBuilder.master("local[2]")
    }
    sparkBuilder.getOrCreate
  }

  def getSparkSessionWithHive(appname: String, local: Boolean): SparkSession = getSparkSession(appname, local, withHive = true)

  def getSparkSessionWithHive(appname: String): SparkSession = getSparkSession(appname, local = false, withHive = true)

  def getSparkSessionLocal(appname: String): SparkSession = getSparkSession(appname, local = true, withHive = false)

  def getSparkSessionWithHiveLocal(appname: String): SparkSession = getSparkSession(appname, local = true, withHive = true)
}
