package com.example.jcaruso.fishproject.utils;

import android.content.Context;
import android.support.design.widget.TextInputEditText;

import com.example.jcaruso.fishproject.R;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.AbstractMap;
import java.util.List;

public class Validator {

    public static final int NOT_EMPTY = 0;
    public static final int IS_A_NUMBER = 1;

    private Context mContext;

    public Validator(Context context) {
        mContext = context;
    }

    public boolean validate(List<AbstractMap.SimpleEntry<Integer, TextInputEditText>> inputs) {
        boolean valid = true;
        for (AbstractMap.SimpleEntry<Integer, TextInputEditText> input : inputs) {
            boolean validInput = true;
            switch (input.getKey()) {
                case NOT_EMPTY:
                    validInput = validateNotEmpty(input.getValue());
                    break;
                case IS_A_NUMBER:
                    validInput = validateIsANumber(input.getValue());
                    break;
                default:
            }
            valid = valid && validInput;
        }
        return valid;
    }

    private boolean validateNotEmpty(TextInputEditText input) {
        String text = input.getText().toString();
        if (text.isEmpty()) {
            input.setError(mContext.getString(R.string.error_empty));
            return false;
        }
        return true;
    }

    private boolean validateIsANumber(TextInputEditText input) {
        String text = input.getText().toString();
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(text, pos);
        return text.length() == pos.getIndex();
    }

    public boolean validateEqualPasswords(TextInputEditText input1, TextInputEditText input2) {
        String password1 = input1.getText().toString();
        String password2 = input2.getText().toString();
        if (!password1.equals(password2)) {
            input1.setError(mContext.getString(R.string.error_equal_password));
            input2.setError(mContext.getString(R.string.error_equal_password));
            return false;
        }
        return true;
    }
}
