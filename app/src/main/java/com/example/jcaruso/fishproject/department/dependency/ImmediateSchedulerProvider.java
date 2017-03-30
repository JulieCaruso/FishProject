package com.example.jcaruso.fishproject.department.dependency;

import android.support.annotation.NonNull;

import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {
    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.single();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.single();
    }
}