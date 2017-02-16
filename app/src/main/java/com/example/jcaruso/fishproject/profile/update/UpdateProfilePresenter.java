package com.example.jcaruso.fishproject.profile.update;

import android.os.Handler;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class UpdateProfilePresenter extends MvpBasePresenter<UpdateProfileView> {

    public UpdateProfilePresenter() {
    }

    public void loadUser() {
        if (isViewAttached())
            getView().showLoadingUpdateProfileForm();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().showUpdateProfileForm();
                    getView().setData(new User("Julie", "C", "bu", "password", "F", new Department("ADM", "chaussée chrleroi", 25, 5), "sdfghj", 5));
                }
            }
        }, 2000);
    }

    public void doUpdateProfile(String firstname, String lastname, String sex, Department department, String username, String password) {
        if (isViewAttached())
            getView().showLoadingUpdateProfile();

        // TODO: call request onSuccess:

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().updateProfileSuccessful();
                }
            }
        }, 2000);
    }

    public void onNewInstance() {
        if (isViewAttached()) {
            getView().showUpdateProfileForm();
            getView().loadData();
        }
    }
}
