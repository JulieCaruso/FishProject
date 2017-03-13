package com.example.jcaruso.fishproject.profile.update;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
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

public class UpdateProfilePresenter extends MvpBasePresenter<UpdateProfileView> {

    private DataService mDataService;

    @Inject
    public UpdateProfilePresenter(DataService dataService) {
        mDataService = dataService;
    }


    public void loadUser() {
        if (isViewAttached())
            getView().showLoadingUpdateProfileForm();

        final User user = App.getUser();
        if (user != null) {
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
                            if (isViewAttached()) {
                                getView().showUpdateProfileForm();
                                getView().setData(user);
                            }
                        }
                    });
        }
    }

    public void doUpdateProfile(Integer userId, String firstname, String lastname, String sex, int departmentId, String username, String password) {
        if (isViewAttached())
            getView().showLoadingUpdateProfile();

        try {
            String hash = User.SHA1(password);
            mDataService.updateProfile(userId, new User(firstname, lastname, username, hash, sex, departmentId, "token", userId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(User user) {
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
                                getView().updateProfileSuccessful();
                        }
                    });
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            if (isViewAttached())
                getView().showError(e);
        }
    }

    public void onNewInstance() {
        if (isViewAttached()) {
            getView().showUpdateProfileForm();
            getView().loadData();
        }
    }
}
