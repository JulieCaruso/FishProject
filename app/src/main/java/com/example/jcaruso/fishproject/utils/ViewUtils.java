package com.example.jcaruso.fishproject.utils;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    /**
     * Enables or disables view and all its children.
     *
     * @param view    view to either enable or disable
     * @param enabled true to enable, false to disable
     */
    public static void enableDisableViewGroup(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                enableDisableViewGroup(child, enabled);
            }
        }
    }

    /**
     * Animates view from alpha 0 to alpha 1 in 500ms.
     *
     * @param view view to animate
     */
    public static void animateToVisible(View view) {
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setDuration(500);
    }

    public static class FullScrollDownRunnable implements Runnable {

        private NestedScrollView mScrollView;

        /**
         * Runnable class implementing a full scroll down.
         *
         * @param scrollView NestedScrollView to scroll down
         */
        public FullScrollDownRunnable(NestedScrollView scrollView) {
            mScrollView = scrollView;
        }

        @Override
        public void run() {
            mScrollView.fullScroll(View.FOCUS_DOWN);
        }
    }
}
