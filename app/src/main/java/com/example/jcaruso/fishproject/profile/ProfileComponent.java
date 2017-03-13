package com.example.jcaruso.fishproject.profile;

import com.example.jcaruso.fishproject.profile.update.UpdateProfilePresenter;
import com.example.jcaruso.fishproject.profile.view.ProfilePresenter;

import dagger.Subcomponent;

@Subcomponent
public interface ProfileComponent {

    ProfilePresenter profilePresenter();

    UpdateProfilePresenter updateProfilePresenter();
}
