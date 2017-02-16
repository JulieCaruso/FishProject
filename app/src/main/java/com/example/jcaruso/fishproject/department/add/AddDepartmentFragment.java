package com.example.jcaruso.fishproject.department.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jcaruso.fishproject.utils.ViewUtils.animateToVisible;

public class AddDepartmentFragment extends MvpViewStateFragment<AddDepartmentView, AddDepartmentPresenter> implements AddDepartmentView {

    @BindView(R.id.add_department_name)
    TextInputEditText mNameInput;

    @BindView(R.id.add_department_address)
    TextInputEditText mAddressInput;

    @BindView(R.id.add_department_employee_nb)
    TextInputEditText mEmployeeNbInput;

    @BindView(R.id.add_department_add_button)
    AppCompatButton mAddButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_department_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mAddButton.setOnClickListener(onClickAdd);
    }

    private View.OnClickListener onClickAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean valid = true;

            String name = mNameInput.getText().toString();
            if (name.isEmpty()) {
                valid = false;
                mNameInput.setError(getString(R.string.error_empty));
            }

            String address = mAddressInput.getText().toString();
            if (address.isEmpty()) {
                valid = false;
                mAddressInput.setError(getString(R.string.error_empty));
            }

            String employeeNbString = mEmployeeNbInput.getText().toString();
            if (employeeNbString.isEmpty()) {
                valid = false;
                mEmployeeNbInput.setError(getString(R.string.error_empty));
            }
            int employeeNb = 0;
            try {
                employeeNb = Integer.parseInt(employeeNbString);
            } catch (NumberFormatException e) {
                valid = false;
                mEmployeeNbInput.setError(getString(R.string.error_not_a_number));
            }

            if (valid)
                presenter.doAddDepartment(name, address, employeeNb);
        }
    };

    @Override
    public ViewState createViewState() {
        setRetainInstance(true);
        return new AddDepartmentViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @Override
    public AddDepartmentPresenter createPresenter() {
        return new AddDepartmentPresenter();
    }

    @Override
    public void showAddDepartmentForm() {
        ((AddDepartmentViewState) viewState).setShowAddDepartmentForm();
        mAddButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        ((AddDepartmentViewState) viewState).setStateShowLoading();
        mAddButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(Throwable exception) {
        ((AddDepartmentViewState) viewState).setStateShowError(exception);
        animateToVisible(mAddButton);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void addDepartmentSuccessful() {
        ((AddDepartmentViewState) viewState).setShowAddDepartmentForm();
        animateToVisible(mAddButton);
        startActivity(MainActivity.createIntent(getContext(), MainActivity.DEPARTMENTS_LIST));
    }
}
