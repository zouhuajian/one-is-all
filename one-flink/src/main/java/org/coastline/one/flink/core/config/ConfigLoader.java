package org.coastline.one.flink.core.config;

import org.apache.flink.api.common.ExecutionConfig;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/6
 */
public interface ConfigLoader {

    ExecutionConfig.GlobalJobParameters load() throws IOException;

}
