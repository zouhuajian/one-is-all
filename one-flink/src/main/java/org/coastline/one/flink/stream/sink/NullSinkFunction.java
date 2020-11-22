package org.coastline.one.flink.stream.sink;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.coastline.one.flink.stream.model.AggregateData;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class NullSinkFunction implements SinkFunction<AggregateData> {

    @Override
    public void invoke(AggregateData value, Context context) throws Exception {
    }
}
