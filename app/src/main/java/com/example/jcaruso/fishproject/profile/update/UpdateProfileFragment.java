package com.example.jcaruso.fishproject.profile.update;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.example.jcaruso.fishproject.utils.Validator;
import com.example.jcaruso.fishproject.utils.ViewUtils;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

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
    NestedScrollView contentView;

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

    private ArrayAdapter<Department> mSpinnerAdapter;

    private User mUser;
    private List<Department> mDepartments;

    private View.OnClickListener onClickUpdate = v -> {
        Validator validator = App.getValidator();

        List<AbstractMap.SimpleEntry<Integer, TextInputEditText>> inputs = new ArrayList<>();
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mFirstnameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mLastnameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mUsernameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mPasswordInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mPasswordConfirmationInput));

        boolean validPasswordConfirmation = validator.validateEqualPasswords(mPasswordInput, mPasswordConfirmationInput);

        if (validator.validate(inputs) && validPasswordConfirmation) {
            String sex = mSexInput.getCheckedRadioButtonId() == R.id.update_profile_sex_m ?
                    getString(R.string.sex_m) : getString(R.string.sex_f);
            int departmentId = ((Department) mDepartmentSpinner.getSelectedItem()).getId();

            presenter.doUpdateProfile(new User(mFirstnameInput, mLastnameInput, mUsernameInput, mPasswordInput, sex, departmentId, mUser.getId()));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mSpinnerAdapter = new ArrayAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<Department>());

        mSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDepartmentSpinner.setAdapter(mSpinnerAdapter);

        mUpdateButton.setOnClickListener(onClickUpdate);

        mPasswordConfirmationInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                contentView.post(new ViewUtils.FullScrollDownRunnable(contentView));
                onClickUpdate.onClick(mUpdateButton);
            }
            return false;
        });
    }

    @Override
    public void loadData() {
        presenter.loadDepartments();
    }

    @Override
    public void setData(User user) {
        mUser = user;
        mFirstnameInput.setText(user.getFirstname());
        mLastnameInput.setText(user.getLastname());
        mSexInput.check(user.getSex().equals(getContext().getString(R.string.sex_m)) ? R.id.update_profile_sex_m : R.id.update_profile_sex_f);
        int position = 0;
        for (Department department : mDepartments) {
            if (department.getId() == user.getDepartmentId())
                mDepartmentSpinner.setSelection(position);
            position++;
        }
        mUsernameInput.setText(user.getUsername());
    }

    @Override
    public void setDepartments(List<Department> departments) {
        mDepartments = departments;
        mSpinnerAdapter.clear();
        mSpinnerAdapter.addAll(mDepartments);
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewState<UpdateProfileView> createViewState() {
        setRetainInstance(true);
        return new UpdateProfileViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @NonNull
    @Override
    public UpdateProfilePresenter createPresenter() {
        return App.getBaseAppComponent().profileComponent().updateProfilePresenter();
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
