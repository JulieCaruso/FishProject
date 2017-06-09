package com.example.jcaruso.fishproject.profile.update;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

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


    public void loadDepartments() {
        if (isViewAttached())
            getView().showLoadingUpdateProfileForm();

        final User user = App.getUser();
        if (user != null) {
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
                            if (isViewAttached()) {
                                getView().showUpdateProfileForm();
                                getView().setData(user);
                            }
                        }
                    });
        } else {
            if (isViewAttached())
                getView().showError(new Throwable("no user"));
        }
    }

    public void doUpdateProfile(User user) {
        if (isViewAttached())
            getView().showLoadingUpdateProfile();

        mDataService.updateProfile(user.getId(), user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                    }

                    @Override
                    public void onNext(RestResponse<User> restResponse) {
                        App.setUser(restResponse.getData());
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
    }

    public void onNewInstance() {
        if (isViewAttached()) {
            getView().showUpdateProfileForm();
            getView().loadData();
        }
    }
}
