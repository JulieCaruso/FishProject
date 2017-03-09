package com.example.jcaruso.fishproject.profile.view;

import android.os.Handler;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class ProfilePresenter extends MvpBasePresenter<ProfileView> {

    public void loadUser(boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isViewAttached()) {
                        getView().setData(new User("Julie", "C", "bu", "password", "F", new Department("ADM", "chaussée chrleroi", 25, 5), "sdfghj", 5));
                        getView().showContent();
                    }
                }
            }, 2000);

        }
    }


}
