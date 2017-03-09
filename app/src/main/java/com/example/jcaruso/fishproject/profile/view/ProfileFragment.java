package com.example.jcaruso.fishproject.profile.view;

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
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, User, ProfileView, ProfilePresenter>
        implements ProfileView, SwipeRefreshLayout.OnRefreshListener {

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

    private User mUser;

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
    public void loadData(boolean pullToRefresh) {
        presenter.loadUser(pullToRefresh);
    }

    @Override
    public void setData(User user) {
        mUser = user;
        mUsername.setText(mUser.getUsername());
        mFirstname.setText(mUser.getFirstname());
        mLastname.setText(mUser.getLastname());
        mSex.setImageResource(mUser.getSex().equals(getString(R.string.sex_f)) ? R.drawable.ic_gender_female_48dp : R.drawable.ic_gender_male_48dp);
        mDepartment.setText(mUser.getDepartment().getName());
    }

    @Override
    public User getData() {
        return mUser;
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @NonNull
    @Override
    public LceViewState<User, ProfileView> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
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
