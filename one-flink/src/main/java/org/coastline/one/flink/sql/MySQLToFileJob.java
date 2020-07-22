package org.coastline.one.flink.sql;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;

/**
 * @author Jay.H.Zou
 * @date 7/22/2020
 */
public class MySQLToFileJob {

    private static final String SOURCE_SQL = "CREATE TABLE redis_info (" +
            "  info_id INT," +
            "  node VARCHAR," +
            "  role VARCHAR," +
            "  connected_clients BIGINT," +
            "  keys INT," +
            "  update_time TIMESTAMP," +
            "  PRIMARY KEY (info_id) NOT ENFORCED" +
            ") WITH (" +
            "   'connector' = 'jdbc'," +
            "   'url' = 'jdbc:mysql://xxxx:3306/redis_manager_refactor'," +
            "   'table-name' = 'node_info_1'," +
            "   'username' = 'root'," +
            "   'password' = 'xxx'," +
            "   'driver' = 'com.mysql.cj.jdbc.Driver'" +
            ")";


    public static void main(String[] args) throws InterruptedException {
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        TableEnvironment tableEnvironment = TableEnvironment.create(settings);

        TableResult result = tableEnvironment.executeSql(SOURCE_SQL);
        result.print();
        /*TableSchema tableSchema = result.getTableSchema();
        String[] fieldNames = tableSchema.getFieldNames();
        for (String fieldName : fieldNames) {
            System.out.println(fieldName);
        }*/
        TableResult result1 = tableEnvironment.executeSql("SELECT * FROM redis_info");
        result1.print();
    }

}
