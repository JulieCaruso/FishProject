package com.example.jcaruso.fishproject.signin;

import com.example.fishapi.model.Department;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface SigninView extends MvpView {

    void loadData();

    void showSigninForm();

    void showLoadingForm();

    void setDepartments(List<Department> departments);

    void showLoading();

    void showError(Throwable exception);

    void signinSuccessful();
}
