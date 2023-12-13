package org.coastline.one.spark.hdfs

import org.apache.spark.sql.types.{IntegerType, LongType, StringType, StructType}
import org.coastline.one.spark.core
import org.coastline.one.spark.core.SparkExecutor

/**
 * 读取 oev 解析后的 editlogs
 *
 * @author Jay.H.Zou
 */
object ProcessEditlogJob {

  def main(args: Array[String]): Unit = {

    val path: String = "file:///Users/zouhuajian/data/projects/jay/one-is-all/one-data/fsimage/editlog.xml"

    val spark = core.SparkExecutor.getSparkSessionLocal("process-edit-logs")

    val schema = new StructType()
      .add("OPCODE", StringType)
      .add("DATA", new StructType()
        .add("TXID", StringType)
        .add("LENGTH", LongType)
        .add("INODEID", LongType)
        .add("PATH", StringType)
        .add("REPLICATION", IntegerType)
        .add("MTIME", StringType)
        .add("ATIME", StringType)
        .add("CLIENT_NAME", StringType)
        .add("CLIENT_MACHINE", StringType)
        .add("OVERWRITE", StringType)
        .add("BLOCK", new StructType()
          .add("BLOCK_ID", StringType)
          .add("NUM_BYTES", StringType)
          .add("GENSTAMP", StringType)
        )
        .add("PERMISSION_STATUS", new StructType()
          .add("USERNAME", StringType)
          .add("GROUPNAME", StringType)
          .add("MODE", StringType)
        )

      )

    import com.databricks.spark.xml._
    spark.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "RECORD")
      .schema(schema)
      .xml(path)
      .show(Integer.MAX_VALUE, truncate = false)
  }
}
