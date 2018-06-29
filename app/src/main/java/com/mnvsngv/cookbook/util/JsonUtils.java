package com.mnvsngv.cookbook.util;

import com.google.gson.Gson;

public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static String convertObjectToJson(Object object) {
        return GSON.toJson(object);
    }
}
