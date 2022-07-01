package org.coastline.one.flink.stream.core.sink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.coastline.one.flink.common.util.ConfigurationTool;

/**
 * @author Jay.H.Zou
 * @date 2021/8/23
 */
public class HBaseSinkFunction<T> extends RichSinkFunction<T> {
    private static final long serialVersionUID = 1L;

    private Connection connection;
    private Table table;

    @Override
    public void open(Configuration parameters) throws Exception {
        Configuration configuration = (Configuration) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        String hbaseTable = configuration.get(ConfigurationTool.HBASE_TABLE);
        String zookeeperQuorum = configuration.get(ConfigurationTool.HBASE_ZOOKEEPER_QUORUM);

        /*org.apache.hadoop.conf.Configuration hbaseConfig = HBaseConfiguration.create();
        hbaseConfig.set(HConstants.ZOOKEEPER_QUORUM, zookeeperQuorum);

        connection = ConnectionFactory.createConnection(hbaseConfig);
        table = connection.getTable(TableName.valueOf(hbaseTable));
        customInit(configuration);*/
    }

    /**
     * user custom initialization
     */
    public void customInit(Configuration configuration) {}

    public Connection getConnection() {
        return connection;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void close() {
        if (table != null) {
            try {
                table.close();
            } catch (Exception ignored) {
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }
    }



}
