package com.example.jcaruso.fishproject.login;

import android.os.Handler;

import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public void doLogin(String username, String password) {
        if (isViewAttached())
            getView().showLoading();

        DataService dataService = App.getBaseAppComponent().dataService();

        // TODO: call request onSuccess:

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().loginSuccessful();
                }
            }
        }, 2000);
    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showLoginForm();
    }

}
