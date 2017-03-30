package com.example.jcaruso.fishproject.dependency;

import android.content.Context;

import com.example.fishapi.service.RestService;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;
import com.example.jcaruso.fishproject.utils.Cache;
import com.example.jcaruso.fishproject.utils.SchedulerProvider;
import com.example.jcaruso.fishproject.utils.Validator;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    public Validator provideValidator() {
        return new Validator(mContext);
    }

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

    @Singleton
    @Provides
    public Cache provideCache() {
        return new Cache();
    }

    @Singleton
    @Provides
    public BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }
}
