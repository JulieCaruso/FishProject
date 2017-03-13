package com.example.jcaruso.fishproject.department.add;

import com.example.fishapi.model.Department;
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

    public void doAddDepartment(String name, String address, int employeeNb) {
        if (isViewAttached())
            getView().showLoading();

        mDataService.addDepartment(new Department(name, address, employeeNb, -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Department>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                    }

                    @Override
                    public void onNext(Department department) {
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
