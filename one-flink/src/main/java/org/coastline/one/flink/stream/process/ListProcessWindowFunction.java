package org.coastline.one.flink.stream.process;

import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/5/23
 */
public class ListProcessWindowFunction<T> extends ProcessWindowFunction<T, List<T>, String, TimeWindow> {
    @Override
    public void process(String s, Context context, Iterable<T> elements, Collector<List<T>> out) throws Exception {

    }
}
