package org.mondora.qoodle.response.utils;

import com.google.gson.Gson;

public class Json {

    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
