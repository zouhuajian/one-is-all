package org.coastline.one.spark.core;

import org.apache.spark.sql.SparkSession;

/**
 * @author Jay.H.Zou
 * @date 2023/1/26
 */
public class SparkExecutor {

    public static SparkSession getSparkSession(String appname, boolean local, boolean withHive) {
        System.setProperty("HADOOP_USER_NAME", "root");
        SparkSession.Builder sparkBuilder = SparkSession.builder();
        sparkBuilder.appName(appname)
                .getOrCreate();
        if (withHive) {
            sparkBuilder.enableHiveSupport()
                    //.config("hive.exec.dynamic.partition", true)
                    .config("hive.exec.dynamic.partition.mode", "nonstrict");
        }
        if (local) {
            sparkBuilder.master("local[2]");
        }
        return sparkBuilder.getOrCreate();
    }

    public static SparkSession getSparkSessionWithHive(String appname, boolean local) {
        return getSparkSession(appname, local, true);
    }

    public static SparkSession getSparkSessionWithHive(String appname) {
        return getSparkSession(appname, false, true);
    }

    public static SparkSession getSparkSessionWithHiveLocal(String appname) {
        return getSparkSession(appname, true, true);
    }


}
