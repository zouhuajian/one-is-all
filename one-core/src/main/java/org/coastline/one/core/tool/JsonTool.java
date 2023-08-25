package org.coastline.one.core.tool;

import com.google.common.collect.Lists;
import com.google.gson.*;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/9/8
 */
public class JsonTool {

    private static final Gson GSON_COMMON = new GsonBuilder().create();

    // 下划线转驼峰
    public static final Gson GSON_LOWER_CASE_WITH_UNDERSCORES = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    // '-' 转驼峰
    public static final Gson GSON_LOWER_CASE_WITH_DASHES = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .create();

    private JsonTool() {
    }

    public static Gson getGsonCommon() {
        return GSON_COMMON;
    }

    public static String toJsonString(Object value) {
        return GSON_COMMON.toJson(value);
    }

    public static JsonElement toJsonElement(String json) {
        return GSON_COMMON.fromJson(json, JsonElement.class);
    }

    public static <T> T toJavaObject(String json, Class<T> clazz) {
        return GSON_COMMON.fromJson(json, clazz);
    }

    public static <T> T toJavaObject(JsonElement json, Class<T> clazz) {
        return GSON_COMMON.fromJson(json, clazz);
    }

    public static <T> List<T> toJavaList(JsonArray json, Class<T[]> clazz) {
        T[] ts = GSON_COMMON.fromJson(json, clazz);
        return Lists.newArrayList(ts);
    }

    public static <T> List<T> toJavaList(String json, Class<T[]> clazz) {
        T[] ts = GSON_COMMON.fromJson(json, clazz);
        return Lists.newArrayList(ts);
    }

}
