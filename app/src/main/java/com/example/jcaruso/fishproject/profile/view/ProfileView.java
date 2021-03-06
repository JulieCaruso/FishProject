package com.example.jcaruso.fishproject.profile.view;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

public interface ProfileView extends MvpLceView<User> {

    void setDepartment(Department department);
}
