package org.coastline.one.spark.job.batch;

import org.apache.spark.sql.SparkSession;

/**
 * @author Jay.H.Zou
 * @date 2023/1/20
 */
public class IcebergReadWriteBatchJob {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("IcebergReaderWriter")
                .master("local[*]") // use the JVM as the master, great for testing
                .config("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
                .config("spark.sql.catalog.iceberg_catalog", "org.apache.iceberg.spark.SparkCatalog")
                .config("spark.sql.catalog.iceberg_catalog.type", "hive")
                .config("spark.sql.catalog.iceberg_catalog.cache-enabled", "false")
                .enableHiveSupport()
                .getOrCreate();
    }
}
