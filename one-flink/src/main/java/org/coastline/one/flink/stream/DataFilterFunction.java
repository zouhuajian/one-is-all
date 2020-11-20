package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FilterFunction;

/**
 * @author zouhuajian
 * @date 2020/11/20
 */
public class DataFilterFunction implements FilterFunction<JSONObject> {
    @Override
    public boolean filter(JSONObject value) throws Exception {
        return value != null;
    }
}
