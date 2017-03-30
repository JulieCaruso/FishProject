package com.example.jcaruso.fishproject.department.view;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.jcaruso.fishproject.service.DataService;
import com.example.jcaruso.fishproject.utils.BaseSchedulerProvider;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DepartmentsPresenter extends MvpBasePresenter<DepartmentsView> {

    private DataService mDataService;
    private BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public DepartmentsPresenter(DataService dataService, BaseSchedulerProvider schedulerProvider) {
        mDataService = dataService;
        mSchedulerProvider = schedulerProvider;
    }

    public void loadDepartments(final boolean pullToRefresh) {
        if (isViewAttached())
            getView().showLoading(pullToRefresh);

        mDataService.getDepartments()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<RestResponse<List<Department>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // onSubscribe
                        System.out.println("onsubscribe");
                    }

                    @Override
                    public void onNext(RestResponse<List<Department>> restResponse) {
                        System.out.println("onnext");
                        if (isViewAttached())
                            getView().setData(restResponse.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onerror " + e);
                        if (isViewAttached())
                            getView().showError(e, pullToRefresh);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("oncomplete");
                        if (isViewAttached())
                            getView().showContent();
                    }
                });
    }
}
