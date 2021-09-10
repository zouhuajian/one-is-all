package org.coastline.one.core;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Jay.H.Zou
 * @date 2021/9/8
 */
public class JsonTool {

    private static final Gson GSON_COMMON = new GsonBuilder().create();

    // 下划线转驼峰
    public static final Gson GSON_LOWER_CASE_WITH_UNDERSCORES = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private JsonTool() {
    }

}
