package com.example.jcaruso.fishproject.home;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void mainActivityTest2() {
        onView(allOf(withId(R.id.login_username), isDisplayed()))
                .perform(replaceText("a"), closeSoftKeyboard());

        onView(allOf(withId(R.id.login_username), withText("a"), isDisplayed()))
                .perform(pressImeActionButton());

        onView(allOf(withId(R.id.login_password), isDisplayed()))
                .perform(replaceText("a"), closeSoftKeyboard());

        onView(allOf(withId(R.id.login_password), withText("a"), isDisplayed()))
                .perform(pressImeActionButton());

        onView(allOf(withId(R.id.main_drawer_button),
                withParent(withId(R.id.main_toolbar)),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.main_drawer_recycler),
                withParent(allOf(withId(R.id.main_drawer_layout),
                        withParent(withId(android.R.id.content)))),
                isDisplayed()))
                .perform(actionOnItemAtPosition(3, click()));

        onView(allOf(withId(R.id.departments_fab), isDisplayed()))
                .perform(click());

        pressBack();

    }

}
