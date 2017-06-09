package com.example.jcaruso.fishproject.login.dependency;

import com.example.jcaruso.fishproject.login.LoginPresenter;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @LoginScope
    @Provides
    LoginPresenter provideLoginPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
        return new LoginPresenter(dataService, schedulerProvider);
    }
}
