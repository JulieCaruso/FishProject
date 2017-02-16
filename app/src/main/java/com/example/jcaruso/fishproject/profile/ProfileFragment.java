package com.example.jcaruso.fishproject.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fishapi.model.User;
import com.example.jcaruso.fishproject.R;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends MvpLceFragment<SwipeRefreshLayout, User, ProfileView, ProfilePresenter> implements ProfileView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.profile_username)
    TextView mUsername;

    @BindView(R.id.profile_firstname)
    TextView mFirstname;

    @BindView(R.id.profile_lastname)
    TextView mLastname;

    @BindView(R.id.profile_sex)
    ImageView mSex;

    @BindView(R.id.profile_department)
    TextView mDepartment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        contentView.setOnRefreshListener(this);
        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Error, click to refresh";
    }

    @NonNull
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }

    @Override
    public void setData(User user) {
        mUsername.setText(user.getUsername());
        mFirstname.setText(user.getFirstname());
        mLastname.setText(user.getLastname());
        mSex.setImageResource(user.getSex().equals(getString(R.string.sex_f)) ? R.drawable.ic_gender_female_48dp : R.drawable.ic_gender_male_48dp);
        mDepartment.setText(user.getDepartment().getName());
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadUser(pullToRefresh);
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
}
