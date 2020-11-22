package org.coastline.common.compare;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 深度对比 json
 *
 * @author Jay.H.Zou
 * @date 2020/11/11
 */
public class CompareJson {

    private static JSONObject origin = new JSONObject();
    private static JSONObject replay = new JSONObject();

    public static void main(String[] args) {
        String v = null;
        System.err.println(JSONObject.parseObject(JSONObject.toJSONString(v)) == null);
        origin.put("one", "one");


        JSONObject two = new JSONObject();
        two.put("two", new ArrayList<>());
        origin.put("two", two);
        origin.put("three", new JSONArray());
        origin.keySet().forEach(key -> {
            // TODO: 如何判断类型？是json还是string等
            Object obj = origin.get(key);
            if (obj instanceof String) {
                System.out.println(obj);
            }
            if (obj instanceof JSONObject) {
                System.out.println(obj);
            }
            if (obj instanceof Integer) {
                System.out.println(obj);
            }

            if (obj instanceof List) {
                System.out.println(obj);
            }

        });
        JSONArray origin = new JSONArray();
        JSONArray replay = new JSONArray();
        origin.equals(replay);
    }

}
