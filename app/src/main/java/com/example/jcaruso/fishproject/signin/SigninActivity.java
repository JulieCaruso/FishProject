package com.example.jcaruso.fishproject.signin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.fishapi.model.Department;
import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.utils.Validator;
import com.example.jcaruso.fishproject.utils.ViewUtils;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jcaruso.fishproject.utils.ViewUtils.animateToVisible;

public class SigninActivity extends MvpViewStateActivity<SigninView, SigninPresenter> implements SigninView {

    @BindView(R.id.loadingView)
    View mLoadingView;

    @BindView(R.id.contentView)
    View mContentView;

    @BindView(R.id.signin_signin_button)
    AppCompatButton mSigninButton;

    @BindView(R.id.signin_firstname)
    TextInputEditText mFirstnameInput;

    @BindView(R.id.signin_lastname)
    TextInputEditText mLastnameInput;

    @BindView(R.id.signin_sex)
    RadioGroup mSexInput;

    @BindView(R.id.signin_department)
    Spinner mDepartmentSpinner;

    @BindView(R.id.signin_username)
    TextInputEditText mUsernameInput;

    @BindView(R.id.signin_password)
    TextInputEditText mPasswordInput;

    @BindView(R.id.signin_password_confirmation)
    TextInputEditText mPasswordConfirmationInput;

    private ArrayAdapter<Department> mSpinnerAdapter;

    private View.OnClickListener onClickSignin = v -> {
        Validator validator = App.getValidator();

        List<AbstractMap.SimpleEntry<Integer, TextInputEditText>> inputs = new ArrayList<>();
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mFirstnameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mLastnameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mUsernameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mPasswordConfirmationInput));

        if (validator.validate(inputs) && validator.validateEqualPasswords(mPasswordInput, mPasswordConfirmationInput)) {
            String sex = mSexInput.getCheckedRadioButtonId() == R.id.signin_sex_m ?
                    getString(R.string.sex_m) : getString(R.string.sex_f);
            Department department = (Department) mDepartmentSpinner.getSelectedItem();

            presenter.doSignin(new User(mFirstnameInput, mLastnameInput, mUsernameInput, mPasswordInput, sex, department.getId()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        ButterKnife.bind(this);

        mSpinnerAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<Department>());

        mSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDepartmentSpinner.setAdapter(mSpinnerAdapter);

        findViewById(R.id.signin_signin_button).setOnClickListener(onClickSignin);
        ((TextInputEditText) findViewById(R.id.signin_password_confirmation)).setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.signin_activity);
                scrollView.post(new ViewUtils.FullScrollDownRunnable(scrollView));
                onClickSignin.onClick(mSigninButton);
            }
            return false;
        });
    }

    @NonNull
    @Override
    public SigninPresenter createPresenter() {
        return App.getBaseAppComponent().signinComponent().signinPresenter();
    }

    @NonNull
    @Override
    public ViewState<SigninView> createViewState() {
        return new SigninViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @Override
    public void loadData() {
        presenter.loadDepartments();
    }

    @Override
    public void showSigninForm() {
        ((SigninViewState) viewState).setShowSigninForm();
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
        mSigninButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoadingForm() {
        ((SigninViewState) viewState).setShowLoadingForm();
        mLoadingView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
    }

    @Override
    public void setDepartments(List<Department> departments) {
        ((SigninViewState) viewState).setShowSigninForm();
        mSpinnerAdapter.clear();
        mSpinnerAdapter.addAll(departments);
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        ((SigninViewState) viewState).setShowLoading();
        mSigninButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(Throwable exception) {
        ((SigninViewState) viewState).setShowError(exception);
        animateToVisible(mSigninButton);
    }

    @Override
    public void signinSuccessful() {
        ((SigninViewState) viewState).setShowSigninForm();
        animateToVisible(mSigninButton);
        setResult(RESULT_OK);
        finish();
    }
}
