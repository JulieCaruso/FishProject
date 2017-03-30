package com.example.jcaruso.fishproject.login;

import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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

    public void doLogin(User user) {
        if (isViewAttached())
            getView().showLoading();

        try {
            String hash = User.encryptSHA1(user.getPassword());
            user.setPassword(hash);
            mDataService.login(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            if (isViewAttached())
                getView().showError(e);
        }
    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showLoginForm();
    }

}
