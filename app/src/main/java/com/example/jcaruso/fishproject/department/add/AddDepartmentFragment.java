package com.example.jcaruso.fishproject.department.add;

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
import android.widget.Toast;

import com.example.fishapi.model.Department;
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

public class AddDepartmentFragment extends MvpViewStateFragment<AddDepartmentView, AddDepartmentPresenter> implements AddDepartmentView {

    @BindView(R.id.add_department_name)
    TextInputEditText mNameInput;

    @BindView(R.id.add_department_address)
    TextInputEditText mAddressInput;

    @BindView(R.id.add_department_employee_nb)
    TextInputEditText mEmployeeNbInput;

    @BindView(R.id.add_department_add_button)
    AppCompatButton mAddButton;

    private View.OnClickListener onClickAdd = v -> {
        Validator validator = App.getValidator();

        List<AbstractMap.SimpleEntry<Integer, TextInputEditText>> inputs = new ArrayList<>();
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mNameInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mAddressInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.NOT_EMPTY, mEmployeeNbInput));
        inputs.add(new AbstractMap.SimpleEntry<>(Validator.IS_A_NUMBER, mEmployeeNbInput));

        if (validator.validate(inputs)) {
            presenter.doAddDepartment(new Department(mNameInput, mAddressInput, mEmployeeNbInput));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_department_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mAddButton.setOnClickListener(onClickAdd);

        mEmployeeNbInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.add_department_fragment);
                scrollView.post(new ViewUtils.FullScrollDownRunnable(scrollView));
                onClickAdd.onClick(mAddButton);
            }
            return false;
        });
    }

    @NonNull
    @Override
    public ViewState createViewState() {
        setRetainInstance(true);
        return new AddDepartmentViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.onNewInstance();
    }

    @NonNull
    @Override
    public AddDepartmentPresenter createPresenter() {
        return App.getInstance().getDepartmentComponent().addDepartmentPresenter();
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
