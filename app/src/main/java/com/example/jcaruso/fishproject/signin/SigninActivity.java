package com.example.jcaruso.fishproject.signin;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jcaruso.fishproject.R;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        findViewById(R.id.signin_signin_button).setOnClickListener(onClickSignin);
    }

    private View.OnClickListener onClickSignin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextInputEditText firstnameInput = (TextInputEditText) findViewById(R.id.signin_firstname);
            TextInputEditText lastnameInput = (TextInputEditText) findViewById(R.id.signin_lastname);
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
                // request signin onSuccess:
                finish();
            }
        }
    };
}
