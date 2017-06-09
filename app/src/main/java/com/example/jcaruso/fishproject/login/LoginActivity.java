package com.example.jcaruso.fishproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.example.jcaruso.fishproject.signin.SigninActivity;
import com.example.jcaruso.fishproject.utils.Validator;
import com.example.jcaruso.fishproject.utils.ViewUtils;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jcaruso.fishproject.utils.ViewUtils.animateToVisible;

public class LoginActivity extends MvpViewStateActivity<LoginView, LoginPresenter> implements LoginView {

    private static final int REQUEST_CODE = 1;

    @BindView(R.id.login_username)
    TextInputEditText mUsernameInput;

    @BindView(R.id.login_password)
    TextInputEditText mPasswordInput;

    @BindView(R.id.login_login_button)
    View mLoginButton;

    @BindView(R.id.login_signin_button)
    View mSigninButton;

    private View.OnClickListener onClickLogin = v -> {
        Validator validator = App.getValidator();

        List<AbstractMap.SimpleEntry<Integer, TextInputEditText>> inputs = new ArrayList<>();
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mUsernameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mPasswordInput));

        if (validator.validate(inputs))
            try {
                presenter.doLogin(new User(mUsernameInput, mPasswordInput));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                showError(e);
            }
        else
            showError(new Exception("Wrong credentials"));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setRetainInstance(true);

        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(onClickLogin);
        mSigninButton.setOnClickListener(v ->
                startActivityForResult(new Intent(LoginActivity.this, SigninActivity.class), REQUEST_CODE));

        mPasswordInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.login_activity);
                scrollView.post(new ViewUtils.FullScrollDownRunnable(scrollView));
                onClickLogin.onClick(mLoginButton);
            }
            return false;
        });
    }


    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return App.getBaseAppComponent().loginComponent().loginPresenter();
    }

    @NonNull
    @Override
    public ViewState<LoginView> createViewState() {
        return new LoginViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @Override
    public void showLoginForm() {
        ((LoginViewState) viewState).setShowLoginForm();
        mUsernameInput.setError(null);
        mPasswordInput.setError(null);
        mSigninButton.setVisibility(View.INVISIBLE);
        mLoginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Throwable exception) {
        ((LoginViewState) viewState).setShowError(exception);
        mSigninButton.setVisibility(View.VISIBLE);
        mLoginButton.setVisibility(View.VISIBLE);
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        ((LoginViewState) viewState).setShowLoading();
        mLoginButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loginSuccessful() {
        mLoginButton.setVisibility(View.VISIBLE);
        animateToVisible(mLoginButton);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            showLoginForm();
        }
    }
}