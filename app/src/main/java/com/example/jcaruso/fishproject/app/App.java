package com.example.jcaruso.fishproject.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.fishapi.dependency.RestServiceModule;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.BuildConfig;
import com.example.jcaruso.fishproject.department.dependency.DepartmentComponent;
import com.example.jcaruso.fishproject.department.dependency.DepartmentModule;
import com.example.jcaruso.fishproject.dependency.AppModule;
import com.example.jcaruso.fishproject.dependency.BaseAppComponent;
import com.example.jcaruso.fishproject.dependency.DaggerBaseAppComponent;
import com.example.jcaruso.fishproject.login.LoginActivity;
import com.example.jcaruso.fishproject.utils.Cache;
import com.example.jcaruso.fishproject.utils.Validator;
import com.google.gson.Gson;

import javax.inject.Inject;

public class App extends Application {

    public static final String USER = "SharedPreferences.USER";

    private static App sInstance;
    private BaseAppComponent mBaseAppComponent;
    @Inject
    public Cache mCache;
    @Inject
    public Gson mGson;
    private SharedPreferences mSharedPreferences;

    public static App getInstance() {
        return sInstance;
    }

    public static void setInstance(App instance) {
        sInstance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        mBaseAppComponent = DaggerBaseAppComponent
                .builder()
                .restServiceModule(new RestServiceModule(BuildConfig.API_URL))
                .appModule(new AppModule(getApplicationContext()))
                .build();

        mSharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);

        // launch initial activity : LoginActivity if not connected, MainActivity otherwise
        if (getUser() == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
        }
    }

    public static BaseAppComponent getBaseAppComponent() {
        return sInstance.mBaseAppComponent;
    }

    public DepartmentComponent getDepartmentComponent() {
        return sInstance.mBaseAppComponent.departmentComponent(new DepartmentModule());
    }

    public static Validator getValidator() {
        return sInstance.mBaseAppComponent.validator();
    }

    public static Cache getCache() {
        return sInstance.mCache;
    }

    public static Gson getGson() {
        return sInstance.mGson;
    }

    public static SharedPreferences getSharedPreferences() {
        return sInstance.mSharedPreferences;
    }

    public static SharedPreferences.Editor editSharedPreferences() {
        return sInstance.mSharedPreferences.edit();
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        sInstance.mSharedPreferences.edit().putString(USER, gson.toJson(user)).apply();
    }

    public static User getUser() {
        String userJson = sInstance.mSharedPreferences.getString(USER, null);
        if (userJson != null) {
            if (getGson() == null)
                return new Gson().fromJson(userJson, User.class);
            return getGson().fromJson(userJson, User.class);
        }
        return null;
    }

    public static void resetUser() {
        sInstance.mSharedPreferences.edit().remove(USER).apply();
    }
}
