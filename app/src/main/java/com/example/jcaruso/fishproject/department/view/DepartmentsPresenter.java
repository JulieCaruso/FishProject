package com.example.jcaruso.fishproject.department.view;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.service.DataService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DepartmentsPresenter extends MvpBasePresenter<DepartmentsView> {

    private DataService mDataService;

    @Inject
    public DepartmentsPresenter(DataService dataService) {
        mDataService = dataService;
    }

    public void loadDepartments(final boolean pullToRefresh) {
        if (isViewAttached())
            getView().showLoading(pullToRefresh);

        mDataService.getDepartments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Department>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                    }

                    @Override
                    public void onNext(List<Department> departments) {
                        if (isViewAttached())
                            getView().setData(departments);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().showError(e, pullToRefresh);
                    }

                    @Override
                    public void onComplete() {
                        if (isViewAttached())
                            getView().showContent();
                    }
                });
    }

}
