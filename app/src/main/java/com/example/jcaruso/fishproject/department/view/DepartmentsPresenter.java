package com.example.jcaruso.fishproject.department.view;

import android.os.Handler;

import com.example.fishapi.model.Department;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class DepartmentsPresenter extends MvpBasePresenter<DepartmentsView> {

    public void loadDepartments(boolean pullToRefresh) {
        if (isViewAttached())
            getView().showLoading(pullToRefresh);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isViewAttached()) {
                    getView().showContent();
                    getView().setData(Department.getDepartments());
                }
            }
        }, 2000);

    }

}
