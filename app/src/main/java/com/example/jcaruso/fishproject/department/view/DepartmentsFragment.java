package com.example.jcaruso.fishproject.department.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.R;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

public class DepartmentsFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<Department>, DepartmentsView, DepartmentsPresenter>
        implements DepartmentsView, SwipeRefreshLayout.OnRefreshListener {

    private DepartmentsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.departments_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contentView.setOnRefreshListener(this);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.departments_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DepartmentsAdapter();
        recycler.setAdapter(mAdapter);
    }

    @Override
    public LceViewState<List<Department>, DepartmentsView> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Department> getData() {
        return mAdapter == null ? null : mAdapter.getItems();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Error, click to refresh";
    }

    @Override
    public DepartmentsPresenter createPresenter() {
        return new DepartmentsPresenter();
    }

    @Override
    public void setData(List<Department> departments) {
        mAdapter.setItems(departments);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadDepartments(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
    }
}
