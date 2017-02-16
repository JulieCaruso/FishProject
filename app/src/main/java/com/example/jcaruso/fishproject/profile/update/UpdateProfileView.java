package com.example.jcaruso.fishproject.profile.update;

import com.example.fishapi.model.User;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface UpdateProfileView extends MvpView {

    void loadData();

    void setData(User user);

    void showLoadingUpdateProfileForm();

    void showUpdateProfileForm();

    void showLoadingUpdateProfile();

    void showError(Throwable exception);

    void updateProfileSuccessful();
}
