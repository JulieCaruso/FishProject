package com.example.jcaruso.fishproject;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.department.add.AddDepartmentPresenter;
import com.example.jcaruso.fishproject.department.dependency.DepartmentComponent;
import com.example.jcaruso.fishproject.department.dependency.DepartmentModule;
import com.example.jcaruso.fishproject.department.view.DepartmentsPresenter;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;

import org.mockito.Mockito;

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
            public AddDepartmentPresenter providesAddDepartmentPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
                return new AddDepartmentPresenter(dataService, schedulerProvider) {
                    @Override
                    public void doAddDepartment(Department department) {
                        getView().addDepartmentSuccessful();
                    }
                };
            }

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
