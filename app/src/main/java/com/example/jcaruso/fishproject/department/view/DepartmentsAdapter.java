package com.example.jcaruso.fishproject.department.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fishapi.model.Department;
import com.example.jcaruso.fishproject.R;

import java.util.ArrayList;
import java.util.List;

public class DepartmentsAdapter extends RecyclerView.Adapter {

    private List<Department> mItems = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DepartmentItemViewHolder(new DepartmentItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DepartmentItemViewHolder viewHolder = (DepartmentItemViewHolder) holder;
        Department department = mItems.get(position);

        viewHolder.mItem.setName(department.getName());
        viewHolder.mItem.setAddress(department.getAddress());
        viewHolder.mItem.setEmployeeNb(department.getEmployeeNb());
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

    private class DepartmentItemViewHolder extends RecyclerView.ViewHolder {

        private DepartmentItemView mItem;

        public DepartmentItemViewHolder(DepartmentItemView itemView) {
            super(itemView);
            mItem = itemView;
        }
    }
}
