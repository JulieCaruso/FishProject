package com.example.jcaruso.fishproject.home;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.login.LoginActivity;
import com.example.jcaruso.fishproject.signin.SigninActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> mItentTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login_login_button), withText("Log In"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.login_signin_button), withText("Sign In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_activity),
                                        0),
                                3),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

    }

    @Test
    public void loginFailedTest() {
        onView(withId(R.id.login_login_button))
                .perform(click());
        onView(withId(R.id.login_signin_button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void signinButtonNotDisplayedTest() {
        onView(withId(R.id.login_signin_button))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void loginSuccessTest() {
        onView(withId(R.id.login_username))
                .perform(typeText("username"), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.login_login_button))
                .perform(click());

        // wait for 2s of login click + 1s extra
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // MainActivity has started
        intended(hasComponent(MainActivity.class.getName()));

        // ProfileFragment is displayed
        onView(withId(R.id.profile_username))
                .check(matches(isDisplayed()));
    }

    @Test
    public void gotToSignInTest() {
        onView(withId(R.id.login_login_button))
                .perform(click());
        onView(withId(R.id.login_signin_button))
                .perform(click());
        intended(hasComponent(SigninActivity.class.getName()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
