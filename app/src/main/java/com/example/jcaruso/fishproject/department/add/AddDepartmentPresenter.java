package com.example.jcaruso.fishproject.department.add;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddDepartmentPresenter extends MvpBasePresenter<AddDepartmentView> {

    private DataService mDataService;

    @Inject
    public AddDepartmentPresenter(DataService dataService) {
        mDataService = dataService;
    }

    public void doAddDepartment(Department department) {
        if (isViewAttached())
            getView().showLoading();

        mDataService.addDepartment(department)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
