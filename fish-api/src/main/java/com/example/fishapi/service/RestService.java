package com.example.fishapi.service;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestService {

    // AUTHENTIFICATION

    @POST("/api/login")
    Observable<RestResponse<User>> login(@Body User user);

    @POST("/api/signin")
    Observable<RestResponse<User>> signin(@Body User user);

    @PUT("/api/update-profile/{id}")
    Observable<RestResponse<User>> updateProfile(@Path("id") Integer userId, @Body User user);

    // DEPARTMENTS

    @GET("/api/departments")
    Observable<RestResponse<List<Department>>> getDepartments();

    @GET("/api/departments/{id}")
    Observable<RestResponse<Department>> getDepartment(@Path("id") Integer departmentId);

    @POST("/api/departments")
    Observable<RestResponse<Department>> addDepartment(@Body Department department);

}
