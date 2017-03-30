package com.example.jcaruso.fishproject;

import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.department.dependency.DepartmentComponent;
import com.example.jcaruso.fishproject.department.dependency.DepartmentModule;
import com.example.jcaruso.fishproject.department.view.DepartmentsPresenter;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;

public class TestApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    @Override
    public DepartmentComponent getDepartmentComponent() {
        return getBaseAppComponent().departmentComponent(new DepartmentModule() {
            @Override
            public DepartmentsPresenter providesDepartmentsPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
                return new DepartmentsPresenter(dataService, schedulerProvider) {
                    @Override
                    public void loadDepartments(boolean pullToRefresh) {
                        System.out.println("Fake presenter");
                    }
                };
            }
        });
    }
}
