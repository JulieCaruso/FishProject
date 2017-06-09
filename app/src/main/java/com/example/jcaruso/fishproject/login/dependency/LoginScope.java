package com.example.jcaruso.fishproject.login.dependency;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
public @interface LoginScope {
}
