package org.coastline.one.flink.sql.jobs.paimon;

import org.apache.flink.table.api.TableEnvironment;
import org.coastline.one.flink.sql.jobs.TableJobExecutor;

/**
 * @author Jay.H.Zou
 * @date 2023/12/13
 */
public class ReadLocalWriteJob {

    public static void main(String[] args) {
        TableEnvironment env = TableJobExecutor.buildTableEnvWithPaimonCatalog();
        env.executeSql("CREATE TABLE temp_table ( " +
                "    item_id STRING, " +
                "    user_id STRING, " +
                "    action  STRING, " +
                "    vtime  STRING " +
                ") WITH ( " +
                "    'connector' = 'filesystem', " +
                "    'path' = 'file:///Users/zouhuajian/data/projects/jay/one-is-all/data/rec_tmall/sample_sam_tianchi_2014002_rec_tmall_log.csv', " +
                "    'format' = 'csv' " +
                ")");

        // sink
        env.executeSql("CREATE TABLE IF NOT EXISTS paimon_oss_catalog.bigdata.rec_tmall_base " +
                "( " +
                "    item_id STRING, " +
                "    user_id STRING, " +
                "    action  STRING, " +
                "    vtime  STRING " +
                ") WITH ('connector' = 'paimon')");

        env.executeSql("INSERT INTO paimon_oss_catalog.bigdata.rec_tmall_base " +
                "SELECT * FROM temp_table");


    }

}