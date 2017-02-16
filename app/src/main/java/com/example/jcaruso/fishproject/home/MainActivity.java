package com.example.jcaruso.fishproject.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.profile.update.UpdateProfileFragment;
import com.example.jcaruso.fishproject.profile.view.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private final static String STATE_SELECTED_ITEM = "MainActivity.STATE_SELECTED_ITEM";

    private List<DrawerItem> mDrawerItems = new ArrayList<DrawerItem>() {{
        // header
        add(new DrawerItem());
        // drawer items
        add(new DrawerItem(R.drawable.ic_account_circle_black_48dp, R.string.view_profile));
        add(new DrawerItem(R.drawable.ic_settings_black_48dp, R.string.update_profile));
        add(new DrawerItem(R.drawable.ic_business_black_48dp, R.string.departments_list));
        add(new DrawerItem(R.drawable.ic_add_black_48dp, R.string.add_department));
        add(new DrawerItem(R.drawable.ic_exit_to_app_black_48dp, R.string.logout));
    }};

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
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout_withdrawer);
        ButterKnife.bind(this);

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
                onItemSelected(1);
            }
        });

        if (savedInstanceState == null)
            onItemSelected(1);
        else {
            mSelectedItem = savedInstanceState.getInt(STATE_SELECTED_ITEM, 1);
            setItemSelected();
        }
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
        switch (position) {
            // View profile
            case 1:
                break;
            // Update profile
            case 2:
                fragment = new UpdateProfileFragment();
                break;
            // Departments list
            case 3:
                break;
            // Add a department
            case 4:
                break;
            // Log out
            case 5:
                finish();
                break;
            default:
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content_frame, fragment).commit();

        mDrawerLayout.closeDrawer(mDrawerRecycler);
    }

    private void setItemSelected() {
        for (DrawerItem item : mDrawerItems) {
            item.setSelected(false);
        }
        mDrawerItems.get(mSelectedItem).setSelected(true);
        mDrawerAdapter.notifyDataSetChanged();
        if (mSelectedItem == 1)
            mProfile.setVisibility(View.INVISIBLE);
        else
            mProfile.setVisibility(View.VISIBLE);
    }
}
