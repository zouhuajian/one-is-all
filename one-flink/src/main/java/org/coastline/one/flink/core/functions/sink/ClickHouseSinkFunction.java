package org.coastline.one.flink.core.functions.sink;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.coastline.one.flink.core.config.ConfigurationTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2021/8/6
 */
public class ClickHouseSinkFunction<T> extends RichSinkFunction<T> {

    private static final long serialVersionUID = 1L;
    private static final String CLICKHOUSE_DRIVER = "com.github.housepower.jdbc.ClickHouseDriver";
    private transient HikariPool pool;

    @Override
    public void open(Configuration parameters) throws Exception {
        Configuration configuration = (Configuration)getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        Properties props = new Properties();
        props.setProperty("driverClassName", CLICKHOUSE_DRIVER);
        props.setProperty("poolName", configuration.get(ConfigurationTool.DATABASE_POOL_NAME));
        props.setProperty("minimumIdle", configuration.get(ConfigurationTool.DATABASE_MINIMUM_IDLE));
        props.setProperty("maximumPoolSize", configuration.get(ConfigurationTool.DATABASE_MAXIMUM_POOL_SIZE));
        props.setProperty("connectionTimeout", "30000");
        props.setProperty("jdbcUrl", configuration.get(ConfigurationTool.DATABASE_JDBC_URL));
        props.setProperty("username", configuration.get(ConfigurationTool.DATABASE_USERNAME));
        props.setProperty("password", configuration.get(ConfigurationTool.DATABASE_PASSWORD));
        HikariConfig hikariConfig = new HikariConfig();
        pool = new HikariPool(hikariConfig);
    }

    @Override
    public void close() {
        if (pool != null) {
            try {
                pool.shutdown();
            } catch (Exception ignored) {
            }
        }
    }

    public int insert(final String sql, final OperationCallback callback) throws Exception {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            callback.callback(preparedStatement);
            return preparedStatement.executeUpdate();
        }
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    public interface OperationCallback {
        void callback(PreparedStatement preparedStatement) throws SQLException;
    }

}
