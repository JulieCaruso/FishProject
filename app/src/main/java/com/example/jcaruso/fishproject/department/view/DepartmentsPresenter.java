package com.example.jcaruso.fishproject.department.view;

import android.os.Handler;

import com.example.fishapi.model.Department;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;

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
                    getView().setData(new ArrayList<Department>() {{
                        add(new Department("ADM", "5 rue du truc", 25, 1));
                        add(new Department("DMA", "5 rue du machin", 1, 2));
                        add(new Department("MDA", "5 rue du chouette", 0, 3));
                    }});
                }
            }
        }, 2000);

    }

}
