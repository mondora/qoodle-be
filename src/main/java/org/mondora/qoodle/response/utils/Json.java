package org.mondora.qoodle.response.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.mondora.qoodle.Qoodles;

import java.lang.reflect.Type;
import java.util.List;

public class Json {

    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    //inserire metodo fromJson per le liste!


    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
