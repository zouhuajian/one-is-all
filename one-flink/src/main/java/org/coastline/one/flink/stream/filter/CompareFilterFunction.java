package org.coastline.one.flink.stream.filter;

import org.apache.flink.api.common.functions.FilterFunction;
import org.coastline.one.flink.stream.model.AggregateData;

/**
 * @author zouhuajian
 * @date 2020/11/21
 */
public class CompareFilterFunction implements FilterFunction<AggregateData> {

    @Override
    public boolean filter(AggregateData value) throws Exception {
        return value.getComputedValue() > 10;
    }
}
