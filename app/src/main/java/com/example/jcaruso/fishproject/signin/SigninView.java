package com.example.jcaruso.fishproject.signin;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SigninView extends MvpView {

    void showSigninForm();

    void showLoading();

    void showError(Throwable exception);

    void signinSuccessful();
}
