package com.example.jcaruso.fishproject.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.department.add.AddDepartmentFragment;
import com.example.jcaruso.fishproject.department.view.DepartmentsFragment;
import com.example.jcaruso.fishproject.login.LoginActivity;
import com.example.jcaruso.fishproject.profile.update.UpdateProfileFragment;
import com.example.jcaruso.fishproject.profile.view.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final String STATE_SELECTED_ITEM = "MainActivity.STATE_SELECTED_ITEM";
    private static final String EXTRA_NEXT_FRAGMENT_ID = "MainActivity.EXTRA_NEXT_FRAGMENT_ID";

    public static final int VIEW_PROFILE = 1;
    private static final int UPDATE_PROFILE = 2;
    public static final int DEPARTMENTS_LIST = 3;
    public static final int ADD_DEPARTMENT = 4;
    private static final int LOG_OUT = 5;

    private List<DrawerItem> mDrawerItems = new ArrayList<>();

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_user_profile)
    View mProfile;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_content_frame)
    RelativeLayout mContent;

    @BindView(R.id.main_drawer_recycler)
    RecyclerView mDrawerRecycler;

    private DrawerAdapter mDrawerAdapter;
    private int mSelectedItem = VIEW_PROFILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout_withdrawer);
        ButterKnife.bind(this);

        initializeDrawerItems();

        mDrawerAdapter = new DrawerAdapter(this);
        mDrawerAdapter.setItems(mDrawerItems);
        mDrawerRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDrawerRecycler.setAdapter(mDrawerAdapter);

        findViewById(R.id.main_drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerRecycler, true);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected(VIEW_PROFILE);
            }
        });

        // First time : do not add initial fragment to back stack
        if (savedInstanceState == null) {
            setItemSelected();
            getSupportFragmentManager().beginTransaction().add(R.id.main_content_frame, new ProfileFragment()).commit();
        } else {
            mSelectedItem = savedInstanceState.getInt(STATE_SELECTED_ITEM, 1);
            setItemSelected();
        }
    }

    public void initializeDrawerItems() {
        // header
        mDrawerItems.add(new DrawerItem());
        // drawer items
        mDrawerItems.add(new DrawerItem(R.drawable.ic_account_circle_black_48dp, R.string.view_profile));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_settings_black_48dp, R.string.update_profile));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_business_black_48dp, R.string.departments_list));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_add_black_48dp, R.string.add_department));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_exit_to_app_black_48dp, R.string.logout));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_ITEM, mSelectedItem);
    }

    @Override
    public void onItemSelected(int position) {
        mSelectedItem = position;
        setItemSelected();

        Fragment fragment = new ProfileFragment();
        switch (mSelectedItem) {
            case VIEW_PROFILE:
                break;
            case UPDATE_PROFILE:
                fragment = new UpdateProfileFragment();
                break;
            case DEPARTMENTS_LIST:
                fragment = new DepartmentsFragment();
                break;
            case ADD_DEPARTMENT:
                fragment = new AddDepartmentFragment();
                break;
            case LOG_OUT:
                App.resetUser();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            default:
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content_frame, fragment)
                .addToBackStack(null)
                .commit();
        if (mSelectedItem != LOG_OUT)
            mDrawerLayout.closeDrawer(mDrawerRecycler);
    }

    private void setItemSelected() {
        for (DrawerItem item : mDrawerItems) {
            item.setSelected(false);
        }
        mDrawerItems.get(mSelectedItem).setSelected(true);
        mDrawerAdapter.notifyDataSetChanged();
        if (mSelectedItem == VIEW_PROFILE)
            mProfile.setVisibility(View.INVISIBLE);
        else
            mProfile.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            int nextFragmentId = intent.getIntExtra(EXTRA_NEXT_FRAGMENT_ID, 1);
            onItemSelected(nextFragmentId);
        }
    }

    public static Intent createIntent(Context context, int nextFragmentId) {
        Intent nextFragment = new Intent(context, MainActivity.class);
        nextFragment.putExtra(EXTRA_NEXT_FRAGMENT_ID, nextFragmentId);
        return nextFragment;
    }
}
