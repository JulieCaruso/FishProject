package com.example.jcaruso.fishproject.dependency;

import com.example.fishapi.service.RestService;
import com.example.jcaruso.fishproject.service.DataService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public DataService provideDataService(RestService restService) {
        return new DataService(restService);
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}
