package com.example.jcaruso.fishproject.service;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.example.fishapi.service.RestService;

import java.util.List;

import io.reactivex.Observable;

public class DataService {

    RestService mRestService;

    public DataService(RestService restService) {
        mRestService = restService;
    }

    public Observable<User> login(User user) {
        return mRestService.login(user);
    }

    public Observable<User> signin(User user) {
        return mRestService.signin(user);
    }

    public Observable<List<Department>> getDepartments() {
        return mRestService.getDepartments();
    }

    public Observable<Department> getDepartment(Integer id) {
        return mRestService.getDepartment(id);
    }

    public Observable<Department> addDepartment(Department department) {
        return mRestService.addDepartment(department);
    }
}
