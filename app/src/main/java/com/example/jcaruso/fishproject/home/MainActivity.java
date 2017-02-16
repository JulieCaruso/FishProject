package com.example.jcaruso.fishproject.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

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

    //private String[] mDrawerItems;

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

    //@BindView(R.id.main_left_drawer)
    //ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout_withdrawer);
        ButterKnife.bind(this);

        //mDrawerItems = getResources().getStringArray(R.array.drawer_items);

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

        onItemSelected(1);

        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, mDrawerItems));
        //mDrawerList.setOnItemClickListener(this);
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content_frame, fragment).commit();

        //mDrawerList.setItemChecked(position, true);
        //mDrawerLayout.closeDrawer(mDrawerList);
    }
    */

    @Override
    public void onItemSelected(int position) {
        setItemSelected(position);

        Fragment fragment = new Fragment();
        switch (position) {
            // View profile
            case 1:
                mProfile.setVisibility(View.INVISIBLE);
                fragment = new ProfileFragment();
                break;
            // Update profile
            case 2:
                mProfile.setVisibility(View.VISIBLE);
                break;
            // Departments list
            case 3:
                mProfile.setVisibility(View.VISIBLE);
                break;
            // Add a department
            case 4:
                mProfile.setVisibility(View.VISIBLE);
                break;
            // Log out
            case 5:
                mProfile.setVisibility(View.VISIBLE);
                finish();
                break;
            default:
                mProfile.setVisibility(View.VISIBLE);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content_frame, fragment).commit();

        mDrawerLayout.closeDrawer(mDrawerRecycler);
    }

    private void setItemSelected(int position) {
        for (DrawerItem item : mDrawerItems) {
            item.setSelected(false);
        }
        mDrawerItems.get(position).setSelected(true);
        mDrawerAdapter.notifyDataSetChanged();
    }
}
