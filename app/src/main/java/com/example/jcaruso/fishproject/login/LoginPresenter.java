package com.example.jcaruso.fishproject.login;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public void doLogin(String username, String password) {
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
                    getView().loginSuccessful();
            }
        }.run();


    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showLoginForm();
    }

}
