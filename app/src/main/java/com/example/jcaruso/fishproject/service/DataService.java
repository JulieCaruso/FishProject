package com.example.jcaruso.fishproject.service;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.fishapi.service.RestService;

import java.util.List;

import io.reactivex.Observable;

public class DataService {

    private RestService mRestService;

    public DataService(RestService restService) {
        mRestService = restService;
    }

    public Observable<RestResponse<User>> login(User user) {
        return mRestService.login(user);
    }

    public Observable<RestResponse<User>> signin(User user) {
        return mRestService.signin(user);
    }

    public Observable<RestResponse<User>> updateProfile(Integer userId, User user) {
        return mRestService.updateProfile(userId, user);
    }

    public Observable<RestResponse<List<Department>>> getDepartments() {
        return mRestService.getDepartments();
    }

    public Observable<RestResponse<Department>> getDepartment(Integer id) {
        return mRestService.getDepartment(id);
    }

    public Observable<RestResponse<Department>> addDepartment(Department department) {
        return mRestService.addDepartment(department);
    }
}
