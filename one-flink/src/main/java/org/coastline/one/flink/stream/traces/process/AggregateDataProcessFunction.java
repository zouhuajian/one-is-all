package org.coastline.one.flink.stream.traces.process;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.common.model.MonitorData;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/8/29
 */
public class AggregateDataProcessFunction extends ProcessFunction<List<MonitorData>, MonitorData> {

    private transient ListState<MonitorData> monitorDataListState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ListStateDescriptor<MonitorData> listStateDescriptor = new ListStateDescriptor<>("monitor_data_list", TypeInformation.of(MonitorData.class));
        monitorDataListState = getRuntimeContext().getListState(listStateDescriptor);

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void processElement(List<MonitorData> value, ProcessFunction<List<MonitorData>, MonitorData>.Context ctx, Collector<MonitorData> out) throws Exception {
        monitorDataListState.update(value);
        out.collect(value.get(0));
        monitorDataListState.clear();
    }
}
