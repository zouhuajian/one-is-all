package org.coastline.one.clickhouse;

import com.alibaba.fastjson.JSONObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author zouhuajian
 * @date 2020/11/25
 */
public class ClickHousePool {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(ClickHousePool.class);

    private static final String CLICKHOUSE_DRIVER = "com.github.housepower.jdbc.ClickHouseDriver";

    private static final String CLICKHOUSE_DRIVER_HTTP = "ru.yandex.clickhouse.ClickHouseDriver";

    private static final String TE_CLICKHOUSE_URL = "jdbc:clickhouse://localhost:9000";

    private HikariPool pool;

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }


    public void initialize() {
        Properties props = new Properties();
        props.setProperty("driverClassName", CLICKHOUSE_DRIVER);
        props.setProperty("jdbcUrl", TE_CLICKHOUSE_URL);
        props.setProperty("username", "default");
        //props.setProperty("password", dbConfig.getPassword());
        props.setProperty("poolName", "clickhouse-pool");
        props.setProperty("minimumIdle", "8");
        props.setProperty("maximumPoolSize", "8");
        // 连接不会过期
        //props.setProperty("maxLifetime", "0");
        // 空闲的连接永不移除
        //props.setProperty("idleTimeout", "0");
        //props.setProperty("connectionTimeout", "60000");
        HikariConfig hikariConfig = new HikariConfig(props);
        pool = new HikariPool(hikariConfig);
    }

    public boolean insert(String sql, BatchInsertCallback callback) throws Exception {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            callback.writeTo(preparedStatement);
            preparedStatement.executeBatch();
        }
        return true;
    }

    public List<JSONObject> select(String sql) throws Exception {
        List<JSONObject> result = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet != null && resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                JSONObject row = new JSONObject();
                for (int i = 1; i < columnCount + 1; i++) {
                    String key = metaData.getColumnLabel(i);
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.put(key, value);
                    }
                }
                result.add(row);
            }
            return result;
        }
    }

    public void close() {
        if (null != pool) {
            try {
                pool.shutdown();
            } catch (Exception ignored) {
            }
        }
    }

    public interface BatchInsertCallback extends Serializable {

        void writeTo(PreparedStatement preparedStatement) throws SQLException;
    }

}
