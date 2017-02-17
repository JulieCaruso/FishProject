package com.example.jcaruso.fishproject.dependency;

import com.example.fishapi.service.RestService;
import com.example.jcaruso.fishproject.service.DataService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AppModule {

    @Singleton
    @Provides
    public DataService provideDataService(Retrofit retrofit) {
        return new DataService(retrofit);
    }
}
