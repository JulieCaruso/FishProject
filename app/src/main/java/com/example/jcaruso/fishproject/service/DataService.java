package com.example.jcaruso.fishproject.service;

import retrofit2.Retrofit;

public class DataService {

    Retrofit mRetrofit;

    public DataService(Retrofit retrofit) {
        mRetrofit = retrofit;
    }
}
