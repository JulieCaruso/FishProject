package com.example.jcaruso.fishproject.signin;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

public class SigninPresenter extends MvpBasePresenter<SigninView> {

    public void doSignin(String firstname, String lastname, String sex, String department, String username, String password) {
        if (isViewAttached())
            getView().showLoading();

        // TODO: call request onSuccess:

        new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isViewAttached())
                    getView().signinSuccessful();
            }
        }.run();
    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showSigninForm();
    }
}
