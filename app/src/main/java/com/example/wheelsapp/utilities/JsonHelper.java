package com.example.wheelsapp.utilities;

import com.google.gson.Gson;

public class JsonHelper {
    public static <T> T fromJson(String json, Class<T> type) {
        return new Gson().fromJson(json,type);
    }

    public static <T> String toJson(T anyObject) {
        return new Gson().toJson(anyObject);
    }
}
