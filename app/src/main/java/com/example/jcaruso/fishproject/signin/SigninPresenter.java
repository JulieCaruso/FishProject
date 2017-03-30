package com.example.jcaruso.fishproject.signin;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SigninPresenter extends MvpBasePresenter<SigninView> {

    private DataService mDataService;

    @Inject
    public SigninPresenter(DataService dataService) {
        mDataService = dataService;
    }

    public void loadDepartments() {
        if (isViewAttached())
            getView().showLoading();

        mDataService.getDepartments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestResponse<List<Department>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                    }

                    @Override
                    public void onNext(RestResponse<List<Department>> restResponse) {
                        if (isViewAttached())
                            getView().setDepartments(restResponse.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().showError(e);
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached())
                            getView().showSigninForm();
                    }
                });
    }

    public void doSignin(User user) {
        if (isViewAttached())
            getView().showLoading();

        try {
            String hash = User.encryptSHA1(user.getPassword());
            user.setPassword(hash);
            mDataService.signin(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RestResponse<User>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // onSubscribe
                        }

                        @Override
                        public void onNext(RestResponse<User> restResponse) {
                            // onNext
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (isViewAttached())
                                getView().showError(e);
                        }

                        @Override
                        public void onComplete() {
                            if (isViewAttached())
                                getView().signinSuccessful();
                        }
                    });
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            if (isViewAttached())
                getView().showError(e);
        }
    }

    public void onNewInstance() {
        if (isViewAttached()) {
            getView().showLoadingForm();
            getView().loadData();
        }
    }
}
