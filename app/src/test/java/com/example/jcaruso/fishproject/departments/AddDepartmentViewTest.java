package com.example.jcaruso.fishproject.departments;

import android.content.ComponentName;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.BuildConfig;
import com.example.jcaruso.fishproject.CustomRobolectricTestRunner;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.TestApp;
import com.example.jcaruso.fishproject.department.add.AddDepartmentFragment;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.mockito.Mockito.verify;

@RunWith(CustomRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public class AddDepartmentViewTest {

    private Department mDepartment;

    private AddDepartmentFragment mAddDepartmentView;

    private TextInputEditText mNameInput;
    private TextInputEditText mAddressInput;
    private TextInputEditText mEmployeeNbInput;
    private AppCompatButton mAddButton;

    @Before
    public void setup() {
        mDepartment = new Department("name", "address", 1, -1);
        mAddDepartmentView = new AddDepartmentFragment();
        SupportFragmentTestUtil.startFragment(mAddDepartmentView);

        mNameInput = (TextInputEditText) mAddDepartmentView.getView().findViewById(R.id.add_department_name);
        mAddressInput = (TextInputEditText) mAddDepartmentView.getView().findViewById(R.id.add_department_address);
        mEmployeeNbInput = (TextInputEditText) mAddDepartmentView.getView().findViewById(R.id.add_department_employee_nb);
        mAddButton = (AppCompatButton) mAddDepartmentView.getView().findViewById(R.id.add_department_add_button);
    }

    @Test
    public void addDepartmentSuccess() {
        mNameInput.setText(mDepartment.getName());
        mAddressInput.setText(mDepartment.getAddress());
        mEmployeeNbInput.setText(String.valueOf(mDepartment.getEmployeeNb()));
        mAddButton.performClick();

        Assert.assertEquals(false, mAddDepartmentView.getView().isAttachedToWindow());
        Assert.assertEquals(false, mAddDepartmentView.getView().isActivated());
    }

    @Test
    public void addDepartmentError() {
        mAddButton.performClick();
        Assert.assertNotNull(mNameInput.getError());
        Assert.assertNotNull(mAddressInput.getError());
        Assert.assertNotNull(mEmployeeNbInput.getError());
    }
}
