package com.example.jcaruso.fishproject.departments;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.RestResponse;
import com.example.jcaruso.fishproject.department.add.AddDepartmentPresenter;
import com.example.jcaruso.fishproject.department.add.AddDepartmentView;
import com.example.jcaruso.fishproject.department.dependency.ImmediateSchedulerProvider;
import com.example.jcaruso.fishproject.service.DataService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddDepartmentPresenterTest {

    @Mock
    private DataService mDataService;
    @Mock
    private AddDepartmentView mView;
    @Mock
    private Throwable mException;

    private static Department DEPARTMENT;
    private static RestResponse<Department> RESPONSE;

    private AddDepartmentPresenter mPresenter;
    private TestObserver<RestResponse<Department>> mObserver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DEPARTMENT = new Department("name", "address", 1, -1);
        RESPONSE = new RestResponse<>(true, DEPARTMENT);

        mPresenter = new AddDepartmentPresenter(mDataService, new ImmediateSchedulerProvider());
        mPresenter.attachView(mView);

        mObserver = TestObserver.create();
    }

    @Test
    public void addDepartmentSuccess() {
        when(mDataService.addDepartment(DEPARTMENT)).thenReturn(Observable.just(RESPONSE));
        mDataService.addDepartment(DEPARTMENT).subscribe(mObserver);
        mPresenter.doAddDepartment(DEPARTMENT);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoading();
        inOrder.verify(mView).addDepartmentSuccessful();

        mObserver.assertNoErrors();
        mObserver.assertComplete();
        mObserver.assertResult(RESPONSE);

        mObserver.onNext(RESPONSE);
        mObserver.onComplete();
        verify(mView).addDepartmentSuccessful();
    }

    @Test
    public void addDepartmentError() {
        when(mDataService.addDepartment(DEPARTMENT)).thenReturn(Observable.error(mException));
        mDataService.addDepartment(DEPARTMENT).subscribe(mObserver);
        mPresenter.doAddDepartment(DEPARTMENT);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoading();
        inOrder.verify(mView).showError(mException);

        mObserver.assertError(mException);
        mObserver.assertNotComplete();

        mObserver.onError(mException);
        verify(mView).showError(mException);
    }
}
