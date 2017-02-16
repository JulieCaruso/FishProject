package com.example.jcaruso.fishproject.profile.update;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

public class UpdateProfileViewState implements ViewState<UpdateProfileView> {

    private final int STATE_SHOW_UPDATE_PROFILE_FORM = 0;
    private final int STATE_SHOW_LOADING_UPDATE_PROFILE_FORM = 1;
    private final int STATE_SHOW_LOADING_UPDATE_PROFILE = 2;
    private final int STATE_SHOW_ERROR = 3;

    private int state = STATE_SHOW_UPDATE_PROFILE_FORM;
    private Throwable mException;

    @Override
    public void apply(UpdateProfileView view, boolean retained) {
        switch (state) {
            case STATE_SHOW_UPDATE_PROFILE_FORM:
                view.showUpdateProfileForm();
                break;
            case STATE_SHOW_LOADING_UPDATE_PROFILE_FORM:
                view.showLoadingUpdateProfileForm();
                break;
            case STATE_SHOW_LOADING_UPDATE_PROFILE:
                view.showLoadingUpdateProfile();
                break;
            case STATE_SHOW_ERROR:
                view.showError(mException);
                break;
        }
    }

    public void setShowUpdateProfileForm() {
        state = STATE_SHOW_UPDATE_PROFILE_FORM;
    }

    public void setShowLoadingUpdateProfileForm() {
        state = STATE_SHOW_LOADING_UPDATE_PROFILE_FORM;
    }

    public void setShowLoadingUpdateProfile() {
        state = STATE_SHOW_LOADING_UPDATE_PROFILE;
    }

    public void setShowError(Throwable exception) {
        mException = exception;
        state = STATE_SHOW_ERROR;
    }
}
