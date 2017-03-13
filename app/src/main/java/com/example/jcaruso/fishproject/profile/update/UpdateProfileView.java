package com.example.jcaruso.fishproject.profile.update;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface UpdateProfileView extends MvpView {

    void loadData();

    void setData(User user);

    void setDepartments(List<Department> departments);

    void showLoadingUpdateProfileForm();

    void showUpdateProfileForm();

    void showLoadingUpdateProfile();

    void showError(Throwable exception);

    void updateProfileSuccessful();
}
