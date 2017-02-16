package com.example.jcaruso.fishproject.signin;

import android.os.Handler;

import com.example.fishapi.model.Department;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class SigninPresenter extends MvpBasePresenter<SigninView> {

    public void doSignin(String firstname, String lastname, String sex, Department department, String username, String password) {
        if (isViewAttached())
            getView().showLoading();

        // TODO: call request onSuccess:

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().signinSuccessful();
                }
            }
        }, 2000);
    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showSigninForm();
    }
}
