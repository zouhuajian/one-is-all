package org.coastline.one.core.tool;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

    public static JsonObject toJsonObject(String json) {
        return GSON_COMMON.fromJson(json, JsonObject.class);
    }

    public static <T> T toJavaObject(String json, Class<T> clazz) {
        return GSON_COMMON.fromJson(json, clazz);
    }

    public static <T> T toJavaObject(JsonObject json, Class<T> clazz) {
        return GSON_COMMON.fromJson(json, clazz);
    }
}
