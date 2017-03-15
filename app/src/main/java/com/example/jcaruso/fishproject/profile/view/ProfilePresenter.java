package com.example.jcaruso.fishproject.profile.view;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends MvpBasePresenter<ProfileView> {

    private DataService mDataService;

    @Inject
    public ProfilePresenter(DataService dataService) {
        mDataService = dataService;
    }

    public void loadUser(final boolean pullToRefresh) {
        if (isViewAttached())
            getView().showLoading(pullToRefresh);

        final User user = App.getUser();
        if (user != null) {

            mDataService.getDepartment(user.getDepartmentId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RestResponse<Department>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // onSubscribe
                        }

                        @Override
                        public void onNext(RestResponse<Department> department) {
                            if (isViewAttached())
                                getView().setDepartment(department.getData());
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (isViewAttached())
                                getView().showError(e, pullToRefresh);
                        }

                        @Override
                        public void onComplete() {
                            if (isViewAttached()) {
                                getView().setData(user);
                                getView().showContent();
                            }
                        }
                    });
        } else {
            if (isViewAttached())
                getView().showError(new Throwable("User not found"), pullToRefresh);
        }
    }
}
