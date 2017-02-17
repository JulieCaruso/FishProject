package com.example.jcaruso.fishproject.dependency;

import com.example.fishapi.dependency.RestServiceModule;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.example.jcaruso.fishproject.service.DataService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RestServiceModule.class})
public interface BaseAppComponent {

    DataService dataService();

    void inject(MainActivity mainActivity);

}
