package com.example.jcaruso.fishproject.home;

public class DrawerItem {

    private int mIdResImage;
    private int mIdResName;
    private boolean mSelected;

    public DrawerItem() {
    }

    public DrawerItem(int idResImage, int idResName) {
        mIdResImage = idResImage;
        mIdResName = idResName;
        mSelected = false;
    }

    public int getIdResImage() {
        return mIdResImage;
    }

    public int getIdResName() {
        return mIdResName;
    }

    public boolean getSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}
