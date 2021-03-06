package com.example.jcaruso.fishproject.department.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fishapi.model.Department;
import com.example.fishapi.utils.viewhelper.ItemTouchHelperAdapter;
import com.example.fishapi.utils.viewhelper.ItemTouchHelperViewHolder;
import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.app.App;
import com.example.jcaruso.fishproject.home.MainActivity;
import com.example.jcaruso.fishproject.utils.RecyclerItemClickListener;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentsFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<Department>, DepartmentsView, DepartmentsPresenter>
        implements DepartmentsView, SwipeRefreshLayout.OnRefreshListener, DepartmentsAdapter.OnStartDragListener {

    @BindView(R.id.departments_fab)
    FloatingActionButton mFab;

    private DepartmentsAdapter mAdapter;
    private ItemTouchHelper mHelper;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0 && mFab.isShown())
                mFab.hide();
            else if (dy < 0 && !mFab.isShown())
                mFab.show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.departments_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        contentView.setOnRefreshListener(this);

        final boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        RecyclerView.LayoutManager layoutManager;
        if (isTablet) {
            layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.departments_recycler);
        recycler.setLayoutManager(layoutManager);
        mAdapter = new DepartmentsAdapter(this);
        recycler.setAdapter(mAdapter);
        recycler.addOnScrollListener(onScrollListener);

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view1, position) ->
                Toast.makeText(getContext(), getData().get(position).getName(), Toast.LENGTH_SHORT).show())
        );

        mHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags;
                if (isTablet)
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                else
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                ((ItemTouchHelperAdapter) recyclerView.getAdapter()).onItemMove(fromPos, toPos);
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                throw new UnsupportedOperationException("Swipe unsupported.");
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                contentView.setEnabled(actionState != ItemTouchHelper.ACTION_STATE_DRAG);

                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder instanceof ItemTouchHelperViewHolder) {
                    ItemTouchHelperViewHolder holder = (ItemTouchHelperViewHolder) viewHolder;
                    holder.onItemSelected();
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (viewHolder instanceof ItemTouchHelperViewHolder) {
                    ItemTouchHelperViewHolder holder = (ItemTouchHelperViewHolder) viewHolder;
                    holder.onItemClear();
                }
            }

            // only drag from handle
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        mHelper.attachToRecyclerView(recycler);

        mFab.setOnClickListener(v -> startActivity(MainActivity.createIntent(getContext(), MainActivity.ADD_DEPARTMENT)));
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mHelper.startDrag(viewHolder);
    }

    @NonNull
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

    @NonNull
    @Override
    public DepartmentsPresenter createPresenter() {
        return App.getInstance().getDepartmentComponent().departmentsPresenter();
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
