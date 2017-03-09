package com.example.jcaruso.fishproject.signin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.utils.ViewUtils;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jcaruso.fishproject.utils.ViewUtils.animateToVisible;

public class SigninActivity extends MvpViewStateActivity<SigninView, SigninPresenter> implements SigninView {

    @BindView(R.id.signin_department)
    Spinner mDepartmentSpinner;

    @BindView(R.id.signin_signin_button)
    AppCompatButton mSigninButton;

    private View.OnClickListener onClickSignin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextInputEditText firstnameInput = (TextInputEditText) findViewById(R.id.signin_firstname);
            TextInputEditText lastnameInput = (TextInputEditText) findViewById(R.id.signin_lastname);
            RadioGroup sexInput = (RadioGroup) findViewById(R.id.signin_sex);
            Spinner departmentInput = (Spinner) findViewById(R.id.signin_department);
            TextInputEditText usernameInput = (TextInputEditText) findViewById(R.id.signin_username);
            TextInputEditText passwordInput = (TextInputEditText) findViewById(R.id.signin_password);
            TextInputEditText passwordConfirmationInput = (TextInputEditText) findViewById(R.id.signin_password_confirmation);

            boolean valid = true;

            String firstname = firstnameInput.getText().toString();
            if (firstname.isEmpty()) {
                valid = false;
                firstnameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String lastname = lastnameInput.getText().toString();
            if (lastname.isEmpty()) {
                valid = false;
                lastnameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String sex = sexInput.getCheckedRadioButtonId() == R.id.signin_sex_m ?
                    getString(R.string.sex_m) : getString(R.string.sex_f);

            String department = departmentInput.getSelectedItem().toString();

            String username = usernameInput.getText().toString();
            if (username.isEmpty()) {
                valid = false;
                usernameInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String password = passwordInput.getText().toString();
            if (password.isEmpty()) {
                valid = false;
                passwordInput.setError(v.getContext().getString(R.string.error_empty));
            }

            String passwordConfirmation = passwordConfirmationInput.getText().toString();
            if (!passwordConfirmation.equals(password)) {
                valid = false;
                passwordConfirmationInput.setError(v.getContext().getString(R.string.error_equal_paswword));
            }

            if (valid) {
                presenter.doSignin(firstname, lastname, sex, new Department(department, "azer", 25, 5), username, password);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(new String[]{"ADM", "DMA", "MDA"}));

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mDepartmentSpinner.setAdapter(adapter);

        findViewById(R.id.signin_signin_button).setOnClickListener(onClickSignin);
        ((TextInputEditText) findViewById(R.id.signin_password_confirmation)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.signin_activity);
                    scrollView.post(new ViewUtils.FullScrollDownRunnable(scrollView));
                    onClickSignin.onClick(mSigninButton);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public SigninPresenter createPresenter() {
        return new SigninPresenter();
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
    public void showSigninForm() {
        ((SigninViewState) viewState).setShowSigninForm();
        mSigninButton.setVisibility(View.VISIBLE);

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
