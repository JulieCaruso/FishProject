package com.example.jcaruso.fishproject.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    public static void animateToVisible(View view) {
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setDuration(500);
    }
}
