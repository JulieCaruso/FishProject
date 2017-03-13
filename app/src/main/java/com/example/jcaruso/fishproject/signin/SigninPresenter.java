package com.example.jcaruso.fishproject.signin;

import com.example.fishapi.model.Department;
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
                .subscribe(new Observer<List<Department>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Department> departments) {
                        if (isViewAttached())
                            getView().setDepartments(departments);
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

    public void doSignin(String firstname, String lastname, String sex, int departmentId, String username, String password) {
        if (isViewAttached())
            getView().showLoading();

        try {
            String hash = User.SHA1(password);
            mDataService.signin(new User(firstname, lastname, username, hash, sex, departmentId, "token", -1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(User user) {
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
