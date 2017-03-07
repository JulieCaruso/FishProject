package com.example.jcaruso.fishproject.department.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fishapi.model.Department;
import com.example.fishapi.viewhelper.ItemTouchHelperAdapter;
import com.example.fishapi.viewhelper.ItemTouchHelperViewHolder;
import com.example.jcaruso.fishproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartmentsAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {

    private List<Department> mItems = new ArrayList<>();
    private OnStartDragListener mDragListener;

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    ;

    public DepartmentsAdapter(OnStartDragListener listener) {
        mDragListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DepartmentItemViewHolder(new DepartmentItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final DepartmentItemViewHolder viewHolder = (DepartmentItemViewHolder) holder;
        Department department = mItems.get(position);

        viewHolder.mItem.setName(department.getName());
        viewHolder.mItem.setAddress(department.getAddress());
        viewHolder.mItem.setEmployeeNb(department.getEmployeeNb());
        viewHolder.mHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    public void setItems(List<Department> items) {
        mItems.clear();
        if (items != null) mItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<Department> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
    }

    private class DepartmentItemView extends LinearLayout {

        private TextView mName;
        private TextView mAddress;
        private TextView mEmployeeNb;

        public DepartmentItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.department_item, this);
            mName = (TextView) findViewById(R.id.department_item_name);
            mAddress = (TextView) findViewById(R.id.department_item_address);
            mEmployeeNb = (TextView) findViewById(R.id.department_item_employee_nb);
        }

        public void setName(String name) {
            mName.setText(name);
        }

        public void setAddress(String address) {
            mAddress.setText(address);
        }

        public void setEmployeeNb(int employeeNb) {
            mEmployeeNb.setText(employeeNb > 1 ?
                    getContext().getString(R.string.employees_nb, employeeNb)
                    : getContext().getString(R.string.employee_nb, employeeNb));
        }
    }

    private class DepartmentItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private DepartmentItemView mItem;
        private ImageView mHandle;

        public DepartmentItemViewHolder(DepartmentItemView itemView) {
            super(itemView);
            mItem = itemView;
            mHandle = (ImageView) itemView.findViewById(R.id.department_item_handle);
        }

        @Override
        public void onItemSelected() {
            int color = ContextCompat.getColor(mItem.getContext(), R.color.medium_grey);
            mItem.setBackgroundColor(Color.argb(Math.round(Color.alpha(color) * 0.15f), Color.red(color), Color.green(color), Color.blue(color)));
        }

        @Override
        public void onItemClear() {
            mItem.setBackgroundColor(0);
        }
    }
}
