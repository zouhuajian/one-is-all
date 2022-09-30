package org.coastline.one.flink.stream.functions.process;

import com.google.common.collect.Lists;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.coastline.one.flink.common.model.WindowData;

/**
 * @author zouhuajian
 * @date 2021/5/19
 */
public class ListWindowProcessFunction<T> extends ProcessWindowFunction<T, WindowData<T>, String, TimeWindow> {

    private ListWindowProcessFunction() {}

    public static <T> ListWindowProcessFunction<T> create() {
        return new ListWindowProcessFunction<>();
    }
    @Override
    public void process(String key, Context context, Iterable<T> elements, Collector<WindowData<T>> out) throws Exception {
        WindowData.WindowDataBuilder<T> builder = WindowData.builder();
        WindowData<T> windowData = builder.window(context.window())
                .dataList(Lists.newArrayList(elements))
                .key(key)
                .build();
        out.collect(windowData);
    }
}
