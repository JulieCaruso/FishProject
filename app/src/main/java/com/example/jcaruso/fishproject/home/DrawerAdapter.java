package com.example.jcaruso.fishproject.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jcaruso.fishproject.R;

import java.util.ArrayList;
import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter {

    private static final int HEADER_VIEW_TYPE = 0;
    private static final int DRAWER_ITEM_VIEW_TYPE = 1;

    @FunctionalInterface
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    private List<DrawerItem> mItems = new ArrayList<>();
    private OnItemSelectedListener mListener;

    public DrawerAdapter(OnItemSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW_TYPE)
            return new DrawerHeaderViewHolder(new DrawerHeaderView(parent.getContext()));
        else // DRAWER_ITEM_VIEW_TYPE
            return new DrawerItemViewHolder(new DrawerItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isPositionHeader(position)) {
            final DrawerItemViewHolder viewHolder = (DrawerItemViewHolder) holder;
            DrawerItem drawerItem = mItems.get(position);

            viewHolder.mItem.setOnClickListener(v ->
                    mListener.onItemSelected(viewHolder.getAdapterPosition()));

            viewHolder.mItem.setImage(drawerItem.getIdResImage());
            viewHolder.mItem.setName(drawerItem.getIdResName());
            viewHolder.mItem.setSelection(drawerItem.getSelected());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return HEADER_VIEW_TYPE;
        else
            return DRAWER_ITEM_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<DrawerItem> items) {
        mItems.clear();
        if (items != null)
            mItems.addAll(items);
        notifyDataSetChanged();
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private class DrawerItemView extends LinearLayout {

        private ImageView mImage;
        private TextView mName;

        public DrawerItemView(Context context) {
            super(context);
            View.inflate(context, R.layout.drawer_item, this);
            mImage = (ImageView) findViewById(R.id.drawer_item_image);
            mName = (TextView) findViewById(R.id.drawer_item_name);
        }

        public void setImage(int idRes) {
            mImage.setImageResource(idRes);
        }

        public void setName(int idRes) {
            mName.setText(idRes);
        }

        public void setSelection(boolean selected) {
            if (selected)
                mName.setTextColor(ContextCompat.getColor(getContext(), R.color.coral));
            else
                mName.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_grey));
        }
    }

    private class DrawerItemViewHolder extends RecyclerView.ViewHolder {

        private DrawerItemView mItem;

        public DrawerItemViewHolder(DrawerItemView itemView) {
            super(itemView);
            mItem = itemView;
        }
    }

    private class DrawerHeaderView extends LinearLayout {

        public DrawerHeaderView(Context context) {
            super(context);
            View.inflate(context, R.layout.drawer_header, this);
        }
    }

    private class DrawerHeaderViewHolder extends RecyclerView.ViewHolder {

        public DrawerHeaderViewHolder(DrawerHeaderView itemView) {
            super(itemView);
        }
    }
}
