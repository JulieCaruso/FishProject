package com.example.jcaruso.fishproject.profile.update;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jcaruso.fishproject.utils.ViewUtils.animateToVisible;
import static com.example.jcaruso.fishproject.utils.ViewUtils.enableDisableViewGroup;

public class UpdateProfileFragment extends MvpViewStateFragment<UpdateProfileView, UpdateProfilePresenter> implements UpdateProfileView {

    @BindView(R.id.loadingView)
    View loadingView;

    @BindView(R.id.errorView)
    View errorView;

    @BindView(R.id.contentView)
    ViewGroup contentView;

    @BindView(R.id.update_profile_firstname)
    TextInputEditText mFirstnameInput;

    @BindView(R.id.update_profile_lastname)
    TextInputEditText mLastnameInput;

    @BindView(R.id.update_profile_sex)
    RadioGroup mSexInput;

    @BindView(R.id.update_profile_sex_m)
    RadioButton mSexMInput;

    @BindView(R.id.update_profile_sex_f)
    RadioButton mSexFInput;

    @BindView(R.id.update_profile_department)
    Spinner mDepartmentSpinner;

    @BindView(R.id.update_profile_username)
    TextInputEditText mUsernameInput;

    @BindView(R.id.update_profile_password)
    TextInputEditText mPasswordInput;

    @BindView(R.id.update_profile_password_confirmation)
    TextInputEditText mPasswordConfirmationInput;

    @BindView(R.id.update_profile_update_button)
    AppCompatButton mUpdateButton;

    private User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>() {{
            add("ADM");
            add("DMA");
            add("MDA");
        }});
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDepartmentSpinner.setAdapter(adapter);

        mUpdateButton.setOnClickListener(onClickUpdate);
    }

    private View.OnClickListener onClickUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean valid = true;

            String firstname = mFirstnameInput.getText().toString();
            if (firstname.isEmpty()) {
                valid = false;
                mFirstnameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String lastname = mLastnameInput.getText().toString();
            if (lastname.isEmpty()) {
                valid = false;
                mLastnameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String sex = "";
            switch (mSexInput.getCheckedRadioButtonId()) {
                case R.id.update_profile_sex_m:
                    sex = getContext().getString(R.string.sex_m);
                    break;
                case R.id.update_profile_sex_f:
                    sex = getContext().getString(R.string.sex_f);
                    break;
            }

            String department = mDepartmentSpinner.getSelectedItem().toString();

            String username = mUsernameInput.getText().toString();
            if (username.isEmpty()) {
                valid = false;
                mUsernameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String password = mPasswordInput.getText().toString();
            if (password.isEmpty()) {
                valid = false;
                mPasswordInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String passwordConfirmation = mPasswordConfirmationInput.getText().toString();
            if (!passwordConfirmation.equals(password)) {
                valid = false;
                mPasswordConfirmationInput.setError(v.getContext().getString(R.string.error_equal_paswword));
            }

            if (valid) {
                presenter.doUpdateProfile(firstname, lastname, sex, new Department(department, "azer", 25, 5), username, password);
            }
        }
    };

    @Override
    public void loadData() {
        presenter.loadUser();
    }

    @Override
    public void setData(User user) {
        mUser = user;
        mFirstnameInput.setText(mUser.getFirstname());
        mLastnameInput.setText(mUser.getLastname());
        mSexInput.check(mUser.getSex().equals(getContext().getString(R.string.sex_m)) ? R.id.update_profile_sex_m : R.id.update_profile_sex_f);
        mDepartmentSpinner.setSelection(1);
        mUsernameInput.setText(mUser.getUsername());
    }

    @Override
    public ViewState<UpdateProfileView> createViewState() {
        setRetainInstance(true);
        return new UpdateProfileViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @Override
    public UpdateProfilePresenter createPresenter() {
        return new UpdateProfilePresenter();
    }

    @Override
    public void showUpdateProfileForm() {
        ((UpdateProfileViewState) viewState).setShowUpdateProfileForm();
        enableDisableViewGroup(contentView, true);
        mUpdateButton.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadingUpdateProfileForm() {
        ((UpdateProfileViewState) viewState).setShowLoadingUpdateProfileForm();
        enableDisableViewGroup(contentView, false);
        mUpdateButton.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadingUpdateProfile() {
        ((UpdateProfileViewState) viewState).setShowLoadingUpdateProfile();
        enableDisableViewGroup(contentView, true);
        mUpdateButton.setVisibility(View.INVISIBLE);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(Throwable exception) {
        ((UpdateProfileViewState) viewState).setShowError(exception);
        enableDisableViewGroup(contentView, true);
        animateToVisible(mUpdateButton);
        contentView.setVisibility(View.INVISIBLE);
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateProfileSuccessful() {
        ((UpdateProfileViewState) viewState).setShowUpdateProfileForm();
        enableDisableViewGroup(contentView, true);
        animateToVisible(mUpdateButton);
        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        startActivity(MainActivity.createIntent(getContext(), MainActivity.VIEW_PROFILE));
    }
}
