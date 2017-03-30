package com.example.jcaruso.fishproject.department.dependency;

import com.example.jcaruso.fishproject.department.add.AddDepartmentPresenter;
import com.example.jcaruso.fishproject.department.view.DepartmentsPresenter;

import dagger.Subcomponent;

@DepScope
@Subcomponent(modules = {DepartmentModule.class})
public interface DepartmentComponent {

    AddDepartmentPresenter addDepartmentPresenter();

    DepartmentsPresenter departmentsPresenter();
}
