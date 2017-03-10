package com.example.jcaruso.fishproject.login;

import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private DataService mDataService;

    @Inject
    public LoginPresenter(DataService dataService) {
        mDataService = dataService;
    }

    public void doLogin(String username, String password) {
        if (isViewAttached())
            getView().showLoading();

        mDataService.login(new User(null, null, username, password, null, -1, null, -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        //checks
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
