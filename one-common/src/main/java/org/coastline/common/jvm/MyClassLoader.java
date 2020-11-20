package org.coastline.common.jvm;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * 自定义类加载器
 *
 * @author Jay.H.Zou
 * @date 2020/9/25
 */
public class MyClassLoader {

    public static void main(String[] args) {
        JSONObject origin = new JSONObject();
        JSONObject object = new JSONObject();
        object.put("money", 4.444444);
        origin.put("info", object);

        System.out.println(origin.toJSONString());

        JSONObject info = origin.getJSONObject("info");
        info.put("money", info.getBigDecimal("money").setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        System.out.println(origin.toJSONString());
    }

}
