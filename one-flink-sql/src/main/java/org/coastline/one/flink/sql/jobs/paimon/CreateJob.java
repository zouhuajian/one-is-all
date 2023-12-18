package org.coastline.one.flink.sql.jobs.paimon;

import org.apache.flink.table.api.TableEnvironment;
import org.coastline.one.flink.sql.jobs.TableJobExecutor;

/**
 * @author Jay.H.Zou
 * @date 2023/12/13
 */
public class CreateJob {

    public static void main(String[] args) {
        TableEnvironment env = TableJobExecutor.buildTableEnv();
        createDatabase(env);
        createTable(env);
    }

    private static void createDatabase(TableEnvironment env) {
        env.executeSql("CREATE DATABASE IF NOT EXISTS paimon_oss_catalog.bigdata");
    }

    private static void createTable(TableEnvironment env) {
        env.executeSql("CREATE TABLE IF NOT EXISTS paimon_oss_catalog.bigdata.item " +
                "( " +
                "    user_id  BIGINT, " +
                "    item_id  BIGINT, " +
                "    behavior STRING, " +
                "    dt       STRING, " +
                "    hh       STRING, " +
                "    PRIMARY KEY (dt, hh, user_id) NOT ENFORCED " +
                ") PARTITIONED BY (dt, hh) " +
                "WITH ('connector' = 'paimon')");
    }
}