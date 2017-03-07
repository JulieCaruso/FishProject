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
                        add(new Department("AMD", "5 rue du ch", 0, 3));
                        add(new Department("DAM", "5 rue du cho", 8, 2));
                        add(new Department("MAD", "5 rue du chou", 55, 88));
                        add(new Department("MMM", "5 rue du c", 8, 1));
                    }});
                }
            }
        }, 2000);

    }

}
