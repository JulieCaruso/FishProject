package com.example.jcaruso.fishproject.login;

import com.example.fishapi.model.RestResponse;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.department.dependency.ImmediateSchedulerProvider;
import com.example.jcaruso.fishproject.service.DataService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {

    @Mock
    private DataService mDataService;
    @Mock
    private LoginView mView;
    @Mock
    private Throwable mException;

    private static User USER;
    private static RestResponse<User> RESPONSE;
    private static RestResponse<User> RESPONSE_NULL;

    private LoginPresenter mPresenter;

    private TestObserver<RestResponse<User>> mObserver;

    @Before
    public void setup() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MockitoAnnotations.initMocks(this);

        String hash = User.encryptSHA1("password");
        USER = new User(null, null, "username", hash, null, -1, null, -1);
        RESPONSE = new RestResponse<>(true, USER);
        RESPONSE_NULL = new RestResponse<>(true, null);

        mPresenter = new LoginPresenter(mDataService, new ImmediateSchedulerProvider());
        mPresenter.attachView(mView);

        mObserver = TestObserver.create();
    }

    @Test
    public void loginSuccess() {
        when(mDataService.login(USER)).thenReturn(Observable.just(RESPONSE));
        mDataService.login(USER).subscribe(mObserver);
        mPresenter.doLogin(USER);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoading();
        inOrder.verify(mView).loginSuccessful();

        mObserver.assertNoErrors();
        mObserver.assertComplete();
        mObserver.assertResult(RESPONSE);

        mObserver.onNext(RESPONSE);
        Assert.assertEquals(USER, App.getUser());
        mObserver.onComplete();
        verify(mView).loginSuccessful();
    }

    @Test
    public void loginErrorResult() {
        when(mDataService.login(USER)).thenReturn(Observable.just(RESPONSE_NULL));
        mDataService.login(USER).subscribe(mObserver);
        mPresenter.doLogin(USER);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoading();
        inOrder.verify(mView).showError(Mockito.any(Throwable.class));
        inOrder.verify(mView).loginSuccessful();

        mObserver.assertNoErrors();
        mObserver.assertComplete();
        mObserver.assertResult(RESPONSE_NULL);

        mObserver.onNext(RESPONSE_NULL);
        verify(mView).showError(Mockito.any(Throwable.class));
        mObserver.onComplete();
        verify(mView).loginSuccessful();
    }

    @Test
    public void loginError() {
        when(mDataService.login(USER)).thenReturn(Observable.error(mException));
        mDataService.login(USER).subscribe(mObserver);
        mPresenter.doLogin(USER);

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoading();
        inOrder.verify(mView).showError(mException);

        mObserver.assertError(mException);
        mObserver.assertNotComplete();

        mObserver.onError(mException);
        verify(mView).showError(mException);
    }

}
