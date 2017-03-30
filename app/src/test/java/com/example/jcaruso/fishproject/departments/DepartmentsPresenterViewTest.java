package com.example.jcaruso.fishproject.departments;


import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.jcaruso.fishproject.department.dependency.ImmediateSchedulerProvider;
import com.example.jcaruso.fishproject.department.view.DepartmentsPresenter;
import com.example.jcaruso.fishproject.department.view.DepartmentsView;
import com.example.jcaruso.fishproject.service.DataService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.when;

public class DepartmentsPresenterViewTest {

    boolean showLoadingCalled = false;
    boolean showContentCalled = false;
    boolean showErrorCalled = false;
    boolean setDataCalled = false;
    boolean loadDataCalled = false;

    DepartmentsView mDepartmentsView = new DepartmentsView() {

        @Override
        public void showLoading(boolean pullToRefresh) {
            showLoadingCalled = true;
            System.out.println("showLoading");
        }

        @Override
        public void showContent() {
            showContentCalled = true;
            System.out.println("showContent");
        }

        @Override
        public void showError(Throwable e, boolean pullToRefresh) {
            showErrorCalled = true;
            System.out.println("showError");
        }

        @Override
        public void setData(List<Department> data) {
            setDataCalled = true;
            System.out.println("setData");
        }

        @Override
        public void loadData(boolean pullToRefresh) {
            loadDataCalled = true;
            System.out.println("loadData");
        }
    };

    private DepartmentsPresenter mDepartmentsPresenter;
    private static List<Department> DEPARTMENTS;
    private static RestResponse<List<Department>> RESPONSE;

    @Mock
    private DataService mDataService;
    @Mock
    private Throwable mException;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DEPARTMENTS = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            DEPARTMENTS.add(new Department("Department" + i, "Address" + i, i * 2, i));

        RESPONSE = new RestResponse<>(true, DEPARTMENTS);

        mDepartmentsPresenter = new DepartmentsPresenter(mDataService, new ImmediateSchedulerProvider());
        mDepartmentsPresenter.attachView(mDepartmentsView);
    }

    @Test
    public void loadDepartmentsSuccess() {
        reset();
        when(mDataService.getDepartments()).thenReturn(Observable.just(RESPONSE));
        mDepartmentsPresenter.loadDepartments(false);

        Assert.assertTrue(showLoadingCalled);
        Assert.assertTrue(setDataCalled);
        Assert.assertTrue(showContentCalled);
    }

    @Test
    public void loadDepEmpty() {
        reset();
        when(mDataService.getDepartments()).thenReturn(Observable.just(new RestResponse<>(true, new ArrayList<>())));
        mDepartmentsPresenter.loadDepartments(false);

        Assert.assertTrue(showLoadingCalled);
        Assert.assertTrue(setDataCalled);
        Assert.assertTrue(showContentCalled);
    }

    @Test
    public void loadDepartmentsError() {
        reset();
        when(mDataService.getDepartments()).thenReturn(Observable.error(mException));
        mDepartmentsPresenter.loadDepartments(false);

        Assert.assertTrue(showLoadingCalled);
        Assert.assertTrue(showErrorCalled);
    }

    public void reset() {
        showLoadingCalled = false;
        showContentCalled = false;
        showErrorCalled = false;
        setDataCalled = false;
        loadDataCalled = false;
    }
}
