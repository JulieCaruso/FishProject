package com.example.jcaruso.fishproject.departments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.BuildConfig;
import com.example.jcaruso.fishproject.CustomRobolectricTestRunner;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.TestApp;
import com.example.jcaruso.fishproject.department.view.DepartmentsAdapter;
import com.example.jcaruso.fishproject.department.view.DepartmentsFragment;
import com.example.jcaruso.fishproject.service.DataService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(CustomRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public class DepartmentsViewTest {

    @Mock
    private DataService mDataService;
    @Mock
    private Throwable mException;
    private static List<Department> DEPARTMENTS;

    private DepartmentsFragment mDepartmentsView;
    private DepartmentsAdapter mAdapter;

    private View mErrorView;
    private View mLoadingView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        DEPARTMENTS = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            DEPARTMENTS.add(new Department("Department" + i, "Address" + i, i * 2, i));

        mDepartmentsView = new DepartmentsFragment();
        SupportFragmentTestUtil.startFragment(mDepartmentsView);

        mAdapter = (DepartmentsAdapter) ((RecyclerView) mDepartmentsView.getView().findViewById(R.id.departments_recycler)).getAdapter();
        mErrorView = mDepartmentsView.getView().findViewById(R.id.errorView);
        mLoadingView = mDepartmentsView.getView().findViewById(R.id.loadingView);
    }

    @Test
    public void displayDepartments() {
        mDepartmentsView.setData(DEPARTMENTS);
        mDepartmentsView.showContent();
        assertEquals(DEPARTMENTS.size(), mAdapter.getItemCount());
    }

    @Test
    public void displayEmpty() {
        mDepartmentsView.setData(new ArrayList<>());
        mDepartmentsView.showContent();
        assertEquals(0, mAdapter.getItemCount());
        assertEquals(View.GONE, mErrorView.getVisibility());
        assertEquals(View.GONE, mLoadingView.getVisibility());
    }

    @Test
    public void displayError() {
        mDepartmentsView.showError(mException, false);
        assertEquals(View.VISIBLE, mErrorView.getVisibility());
    }
}
