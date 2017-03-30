package com.example.jcaruso.fishproject.utils;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
