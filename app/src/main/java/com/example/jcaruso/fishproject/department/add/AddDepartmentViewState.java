package com.example.jcaruso.fishproject.department.add;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class AddDepartmentViewState implements ViewState<AddDepartmentView> {

    private final static int STATE_SHOW_ADD_DEPARTMENT_FORM = 0;
    private final static int STATE_SHOW_LOADING = 1;
    private final static int STATE_SHOW_ERROR = 2;

    private int state = STATE_SHOW_ADD_DEPARTMENT_FORM;
    private Throwable mException;

    @Override
    public void apply(AddDepartmentView view, boolean retained) {
        switch (state) {
            case STATE_SHOW_ADD_DEPARTMENT_FORM:
                view.showAddDepartmentForm();
                break;
            case STATE_SHOW_LOADING:
                view.showLoading();
                break;
            case STATE_SHOW_ERROR:
                view.showError(mException);
                break;
        }
    }

    public void setShowAddDepartmentForm() {
        state = STATE_SHOW_ADD_DEPARTMENT_FORM;
    }

    public void setStateShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setStateShowError(Throwable exception) {
        mException = exception;
        state = STATE_SHOW_ERROR;
    }
}
