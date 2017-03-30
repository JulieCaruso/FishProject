package com.example.fishapi.model;

import com.google.gson.annotations.Expose;

public class RestResponse<T> {

    @Expose
    private boolean success;
    @Expose
    private T data;

    public RestResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}
