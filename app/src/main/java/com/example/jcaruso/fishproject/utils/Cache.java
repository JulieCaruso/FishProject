package com.example.jcaruso.fishproject.utils;

import com.example.jcaruso.fishproject.app.App;

import java.util.HashMap;

public class Cache {

    private final HashMap<String, Object> mData = new HashMap<>();

    @SuppressWarnings("unchecked")
    private <T> T getValue(String key) {
        return (T) mData.get(key);
    }

    private void setValue(String key, Object value) {
        mData.put(key, value);
    }

    public static <T> T get(String key) {
        return App.getCache().getValue(key);
    }

    public static void set(String key, Object value) {
        App.getCache().setValue(key, value);
    }
}
