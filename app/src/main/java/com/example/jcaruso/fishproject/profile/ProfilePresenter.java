package com.example.jcaruso.fishproject.profile;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class ProfilePresenter extends MvpBasePresenter<ProfileView> {

    public void loadUser(boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
            getView().setData(new User("Julie", "C", "bu", "password", "F", new Department("ADM", "chaussée chrleroi", 25, 5), "sdfghj", 5));
            getView().showContent();
        }
    }


}
