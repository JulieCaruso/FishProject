package com.example.jcaruso.fishproject.departments;


import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.jcaruso.fishproject.department.dependency.ImmediateSchedulerProvider;
import com.example.jcaruso.fishproject.department.view.DepartmentsPresenter;
import com.example.jcaruso.fishproject.department.view.DepartmentsView;
import com.example.jcaruso.fishproject.service.DataService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

// RUN TESTS ONE BY ONE

public class DepartmentsPresenterTest {

    @Mock
    private DataService mDataService;
    @Mock
    private DepartmentsView mDepartmentsView;
    @Mock
    private Throwable mException;

    private static List<Department> DEPARTMENTS;
    private static RestResponse<List<Department>> RESPONSE;

    private DepartmentsPresenter mDepartmentsPresenter;

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
        when(mDataService.getDepartments()).thenReturn(Observable.just(RESPONSE));
        mDepartmentsPresenter.loadDepartments(false);

        InOrder inOrder = inOrder(mDepartmentsView);
        inOrder.verify(mDepartmentsView).showLoading(false);
        inOrder.verify(mDepartmentsView).setData(DEPARTMENTS);
        inOrder.verify(mDepartmentsView).showContent();
    }

    @Test
    public void loadDepEmpty() {
        when(mDataService.getDepartments()).thenReturn(Observable.just(new RestResponse<>(true, new ArrayList<>())));
        mDepartmentsPresenter.loadDepartments(false);

        InOrder inOrder = inOrder(mDepartmentsView);
        inOrder.verify(mDepartmentsView).showLoading(false);
        inOrder.verify(mDepartmentsView).setData(new ArrayList<>());
        inOrder.verify(mDepartmentsView).showContent();
    }

    @Test
    public void loadDepartmentsError() {
        when(mDataService.getDepartments()).thenReturn(Observable.error(mException));
        mDepartmentsPresenter.loadDepartments(false);

        InOrder inOrder = inOrder(mDepartmentsView);
        System.out.println(mException);
        inOrder.verify(mDepartmentsView).showLoading(false);
        inOrder.verify(mDepartmentsView).showError(mException, false);
    }

    @Test
    public void testGetDepartments() {
        TestObserver<RestResponse<List<Department>>> observer = TestObserver.create();
        when(mDataService.getDepartments()).thenReturn(Observable.just(RESPONSE));
        mDataService.getDepartments().subscribe(observer);
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertResult(RESPONSE);
    }
}
