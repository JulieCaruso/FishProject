package com.example.jcaruso.fishproject.signin;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class SigninViewState implements ViewState<SigninView> {

    private final int STATE_SHOW_SIGNIN_FORM = 0;
    private final int STATE_SHOW_LOADING = 1;
    private final int STATE_SHOW_ERROR = 2;

    private int state = STATE_SHOW_SIGNIN_FORM;
    private Throwable mException;

    @Override
    public void apply(SigninView view, boolean retained) {
        switch (state) {
            case STATE_SHOW_SIGNIN_FORM:
                view.showSigninForm();
                break;
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;
            case STATE_SHOW_ERROR:
                view.showError(mException);
                break;
        }
    }

    public void setShowSigninForm() {
        state = STATE_SHOW_SIGNIN_FORM;
    }

    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setShowError(Throwable exception) {
        mException = exception;
        state = STATE_SHOW_ERROR;
    }
}
