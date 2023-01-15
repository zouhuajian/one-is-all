package org.coastline.one.flink.core.functions.process;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.common.model.MonitorData;

/**
 * @author Jay.H.Zou
 * @date 2021/9/26
 */
public class CommonProcessFunction extends ProcessFunction<MonitorData, MonitorData> {
    private CommonProcessFunction(){}

    public static CommonProcessFunction create() {
        return new CommonProcessFunction();
    }

    @Override
    public void processElement(MonitorData value, ProcessFunction<MonitorData, MonitorData>.Context ctx, Collector<MonitorData> out) throws Exception {
        out.collect(value);
    }

}
