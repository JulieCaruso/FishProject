package com.example.jcaruso.fishproject.login.dependency;

import com.example.jcaruso.fishproject.login.LoginPresenter;

import dagger.Subcomponent;

@LoginScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
    LoginPresenter loginPresenter();
}
