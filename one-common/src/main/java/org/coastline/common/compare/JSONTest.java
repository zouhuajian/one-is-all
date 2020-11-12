package org.coastline.common.compare;

import com.alibaba.fastjson.JSONObject;

public class JSONTest {
    public static void main(String[] args) {
        String json = "{\"share_info\": {\n" +
                "            \"title\": \"df\",\n" +
                "            \"desc\": null\n" +
                "        }}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        System.out.println(jsonObject.getJSONObject("share_info").getDouble("title"));
    }

}
