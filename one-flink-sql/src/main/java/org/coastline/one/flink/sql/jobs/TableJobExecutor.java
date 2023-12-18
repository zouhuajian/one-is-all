package org.coastline.one.flink.sql.jobs;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.CatalogDescriptor;

/**
 * @author Jay.H.Zou
 * @date 2023/12/16
 */
public class TableJobExecutor {

    public static final String PAIMON_OSS_CATALOG = "paimon_oss_catalog";
    private static final String OSS_WAREHOUSE_ROOT = "oss://coastline/warehouse";
    private static final String ACCESS_KEY_ID = "xx";
    private static final String ACCESS_KEY_SECRET = "xx";


    public static TableEnvironment buildTableEnv() {
        return buildTableEnv(null);
    }

    public static TableEnvironment buildTableEnv(Configuration userConf) {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        if (userConf != null) {
            configuration.addAll(userConf);
        }
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .withConfiguration(configuration)
                .inStreamingMode()
                .build();
        return StreamTableEnvironment.create(env, settings);
    }


    public static TableEnvironment buildTableEnvWithPaimonCatalog() {
        Configuration userConf = new Configuration();
        // conf catalog
        userConf.setString("type", "paimon");
        userConf.setString("warehouse", "oss://coastline/warehouse");
        userConf.setString("fs.oss.endpoint", "cn-shanghai.oss.aliyuncs.com");
        userConf.setString("fs.oss.accessKeyId", ACCESS_KEY_ID);
        userConf.setString("fs.oss.accessKeySecret", ACCESS_KEY_SECRET);
        TableEnvironment env = buildTableEnv(userConf);
        env.createCatalog(PAIMON_OSS_CATALOG, CatalogDescriptor.of(PAIMON_OSS_CATALOG, env.getConfig().getConfiguration()));
        return env;
    }

}
