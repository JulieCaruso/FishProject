package com.example.jcaruso.fishproject.signin;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class SigninViewState implements ViewState<SigninView> {

    private static final int STATE_SHOW_SIGNIN_FORM = 0;
    private static final int STATE_SHOW_LOADING_FORM = 1;
    private static final int STATE_SHOW_LOADING = 2;
    private static final int STATE_SHOW_ERROR = 3;

    private int state = STATE_SHOW_LOADING_FORM;
    private Throwable mException;

    @Override
    public void apply(SigninView view, boolean retained) {
        switch (state) {
            case STATE_SHOW_SIGNIN_FORM:
                view.showSigninForm();
                break;
            case STATE_SHOW_LOADING_FORM:
                view.showLoadingForm();
                break;
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;
            case STATE_SHOW_ERROR:
                view.showError(mException);
                break;
            default:
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

    public void setShowLoadingForm() {
        state = STATE_SHOW_LOADING_FORM;
    }
}
