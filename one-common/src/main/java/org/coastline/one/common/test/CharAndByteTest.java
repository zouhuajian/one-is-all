package org.coastline.one.common.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 2021/7/22
 */
public class CharAndByteTest {

    public static void main(String[] args) {
        String origin = "";
        JSONArray data = JSONObject.parseObject(origin).getJSONArray("data");
        Set<Long> timeSet = new HashSet<>();
        for (Object datum : data) {
            JSONObject one = (JSONObject) datum;
            long time = one.getLongValue("time");
            boolean contains = timeSet.contains(time);
            if (contains) {
                System.out.println(time);
            }
            timeSet.add(time);
        }

    }
}
