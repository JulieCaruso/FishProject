package com.example.jcaruso.fishproject.department.add;

import android.os.Handler;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class AddDepartmentPresenter extends MvpBasePresenter<AddDepartmentView> {

    public void doAddDepartment(String name, String address, int employeeNb) {
        if (isViewAttached())
            getView().showLoading();

        // TODO: call request onSuccess:

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().addDepartmentSuccessful();
                }
            }
        }, 2000);
    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showAddDepartmentForm();
    }

}
