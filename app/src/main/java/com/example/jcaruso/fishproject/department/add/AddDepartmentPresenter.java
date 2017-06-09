package com.example.jcaruso.fishproject.department.add;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AddDepartmentPresenter extends MvpBasePresenter<AddDepartmentView> {

    private DataService mDataService;
    private BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public AddDepartmentPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
        mDataService = dataService;
        mSchedulerProvider = schedulerProvider;
    }

    public void doAddDepartment(Department department) {
        if (isViewAttached())
            getView().showLoading();

        mDataService.addDepartment(department)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<RestResponse<Department>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                    }

                    @Override
                    public void onNext(RestResponse<Department> restResponse) {
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
                            getView().addDepartmentSuccessful();
                    }
                });

    }

    public void onNewInstance() {
        if (isViewAttached())
            getView().showAddDepartmentForm();
    }

}
