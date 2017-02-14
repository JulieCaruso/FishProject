package com.example.jcaruso.fishproject.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    void showLoginForm();

    void showError(Throwable exception);

    void showLoading();

    void loginSuccessful();

}
