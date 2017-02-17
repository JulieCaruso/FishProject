package com.example.jcaruso.fishproject.utils;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

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

    public static class FullScrollDownRunnable implements Runnable {

        private NestedScrollView mScrollView;

        public FullScrollDownRunnable(NestedScrollView scrollView) {
            mScrollView = scrollView;
        }

        @Override
        public void run() {
            mScrollView.fullScroll(View.FOCUS_DOWN);
        }
    }
}
