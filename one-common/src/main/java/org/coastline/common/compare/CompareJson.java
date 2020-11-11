package org.coastline.common.compare;

import com.alibaba.fastjson.JSONObject;

/**
 * 深度对比 json
 * @author Jay.H.Zou
 * @date 2020/11/11
 */
public class CompareJson {

    private static JSONObject origin = new JSONObject();
    private static JSONObject replay = new JSONObject();

    public static void main(String[] args) {
        origin.keySet().forEach(key -> {
            // TODO: 如何判断类型？是json还是string等
            String string = replay.getString(key);

        });
    }
}
