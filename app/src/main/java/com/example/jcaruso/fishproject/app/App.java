package com.example.jcaruso.fishproject.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.jcaruso.fishproject.BuildConfig;
import com.example.jcaruso.fishproject.login.LoginActivity;

public class App extends Application {

    private static final String USER = "SharedPreferences.USER";

    private static App sInstance;
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

        mSharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if (!mSharedPreferences.contains(USER)) {
            mSharedPreferences.edit().putString(USER, null).apply();
        }

        // launch activity
        if (mSharedPreferences.getString(USER, null) == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
        }
    }

    public static SharedPreferences getSharedPreferences() {
        return sInstance.mSharedPreferences;
    }


}
