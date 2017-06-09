package com.example.jcaruso.fishproject.login;

import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private DataService mDataService;
    private BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public LoginPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
        mDataService = dataService;
        mSchedulerProvider = schedulerProvider;
    }

    public void doLogin(User user) {
        if (isViewAttached())
            getView().showLoading();

        mDataService.login(user)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<RestResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                    }

                    @Override
                    public void onNext(RestResponse<User> restResponse) {
                        User user = restResponse.getData();
                        if (user == null && isViewAttached())
                            getView().showError(new Throwable("user null"));
                        else
                            App.setUser(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().showError(e);
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached())
                            getView().loginSuccessful();
                    }
                });
    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showLoginForm();
    }

}
