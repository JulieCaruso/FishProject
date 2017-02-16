package com.example.jcaruso.fishproject.department.add;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface AddDepartmentView extends MvpView {

    void showAddDepartmentForm();

    void showLoading();

    void showError(Throwable exception);

    void addDepartmentSuccessful();
}
