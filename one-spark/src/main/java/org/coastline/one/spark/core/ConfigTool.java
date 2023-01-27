package org.coastline.one.spark.core;

import org.apache.spark.SparkConf;

/**
 * @author Jay.H.Zou
 * @date 2023/1/26
 */
public class ConfigTool {

    /**
     * load spark config
     * @return
     */
    private static SparkConf loadConfig() {
        SparkConf sparkConf = new SparkConf();
        // load config
        return sparkConf;
    }

}
