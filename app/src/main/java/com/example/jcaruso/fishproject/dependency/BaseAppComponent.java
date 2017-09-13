package com.example.jcaruso.fishproject.dependency;

import com.example.fishapi.dependency.RestServiceModule;
import com.example.jcaruso.fishproject.auth.AuthenticatorManager;
import com.example.jcaruso.fishproject.department.dependency.DepartmentComponent;
import com.example.jcaruso.fishproject.department.dependency.DepartmentModule;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.example.jcaruso.fishproject.login.dependency.LoginComponent;
import com.example.jcaruso.fishproject.profile.ProfileComponent;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.signin.SigninComponent;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;
import com.example.jcaruso.fishproject.utils.Validator;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RestServiceModule.class})
public interface BaseAppComponent {

    DataService dataService();

    Validator validator();

    BaseSchedulerProvider baseSchedulerProvider();

    void inject(MainActivity mainActivity);

    DepartmentComponent departmentComponent(DepartmentModule departmentModule);

    LoginComponent loginComponent();

    SigninComponent signinComponent();

    ProfileComponent profileComponent();

    AuthenticatorManager authenticatorManager();
}
