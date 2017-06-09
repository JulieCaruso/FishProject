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
import static org.mockito.Mockito.verify;
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
    private static RestResponse<List<Department>> EMPTY_RESPONSE;
    private static RestResponse<List<Department>> RESPONSE;

    private DepartmentsPresenter mDepartmentsPresenter;
    private TestObserver<RestResponse<List<Department>>> mObserver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DEPARTMENTS = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            DEPARTMENTS.add(new Department("Department" + i, "Address" + i, i * 2, i));

        RESPONSE = new RestResponse<>(true, DEPARTMENTS);
        EMPTY_RESPONSE = new RestResponse<>(true, new ArrayList<>());

        mDepartmentsPresenter = new DepartmentsPresenter(mDataService, new ImmediateSchedulerProvider());
        mDepartmentsPresenter.attachView(mDepartmentsView);

        mObserver = TestObserver.create();
    }

    @Test
    public void loadDepartmentsSuccess() {
        when(mDataService.getDepartments()).thenReturn(Observable.just(RESPONSE));
        mDataService.getDepartments().subscribe(mObserver);
        mDepartmentsPresenter.loadDepartments(false);

        InOrder inOrder = inOrder(mDepartmentsView);
        inOrder.verify(mDepartmentsView).showLoading(false);
        inOrder.verify(mDepartmentsView).setData(DEPARTMENTS);
        inOrder.verify(mDepartmentsView).showContent();

        mObserver.assertNoErrors();
        mObserver.assertComplete();
        mObserver.assertResult(RESPONSE);

        mObserver.onNext(RESPONSE);
        verify(mDepartmentsView).setData(DEPARTMENTS);
        mObserver.onComplete();
        verify(mDepartmentsView).showContent();
    }

    @Test
    public void loadDepEmpty() {
        when(mDataService.getDepartments()).thenReturn(Observable.just(EMPTY_RESPONSE));
        mDataService.getDepartments().subscribe(mObserver);
        mDepartmentsPresenter.loadDepartments(false);

        InOrder inOrder = inOrder(mDepartmentsView);
        inOrder.verify(mDepartmentsView).showLoading(false);
        inOrder.verify(mDepartmentsView).setData(new ArrayList<>());
        inOrder.verify(mDepartmentsView).showContent();

        mObserver.assertNoErrors();
        mObserver.assertComplete();
        mObserver.assertResult(EMPTY_RESPONSE);

        mObserver.onNext(EMPTY_RESPONSE);
        verify(mDepartmentsView).setData(new ArrayList<>());
        mObserver.onComplete();
        verify(mDepartmentsView).showContent();
    }

    @Test
    public void loadDepartmentsError() {
        when(mDataService.getDepartments()).thenReturn(Observable.error(mException));
        mDataService.getDepartments().subscribe(mObserver);
        mDepartmentsPresenter.loadDepartments(false);

        InOrder inOrder = inOrder(mDepartmentsView);
        inOrder.verify(mDepartmentsView).showLoading(false);
        inOrder.verify(mDepartmentsView).showError(mException, false);

        mObserver.assertError(mException);
        mObserver.assertNotComplete();

        mObserver.onError(mException);
        verify(mDepartmentsView).showError(mException, false);
    }
}
