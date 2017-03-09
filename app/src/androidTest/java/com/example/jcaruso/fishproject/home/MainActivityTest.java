package com.example.jcaruso.fishproject.home;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Checks;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.jcaruso.fishproject.R;
import com.example.jcaruso.fishproject.department.view.DepartmentsAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void drawerClickProfileTest() {
        onView(withId(R.id.main_drawer_layout))
                .perform(open());

        onView(allOf(withText(R.string.view_profile)))
                .perform(click());

        onView(withId(R.id.profile_username))
                .check(matches(isDisplayed()));
    }

    @Test
    public void toolbarClickProfileTest() {
        onView(withId(R.id.main_drawer_layout))
                .perform(open());

        onView(allOf(withText(R.string.departments_list)))
                .perform(click());

        onView(withId(R.id.main_user_profile))
                .check(matches(isDisplayed()));

        onView(withId(R.id.main_user_profile))
                .perform(click());

        onView(withId(R.id.profile_username))
                .check(matches(isDisplayed()));

        onView(withId(R.id.main_user_profile))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void backTest() {
        onView(withId(R.id.main_drawer_layout))
                .perform(open());

        onView(allOf(withText(R.string.departments_list)))
                .perform(click());

        pressBack();

        onView(withId(R.id.profile_username))
                .check(matches(isDisplayed()));
    }

    @Test
    public void departmentTest() {
        onView(withId(R.id.main_drawer_layout))
                .perform(open());

        onView(allOf(withText(R.string.departments_list)))
                .perform(click());

        //onView(withId(R.id.departments_recycler))
        //        .perform(RecyclerViewActions.scrollToPosition(6));

        onView(withId(R.id.departments_recycler))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("MMM")), scrollTo()));

        onView(withText("MMM"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void departmentMatcherTest() {
        onView(withId(R.id.main_drawer_layout))
                .perform(open());

        onView(allOf(withText(R.string.departments_list)))
                .perform(click());

        onView(withId(R.id.departments_recycler))
                .perform(RecyclerViewActions.scrollToHolder(withTitle("MMM")))
                .perform(click());

        onView(withText("MAD"))
                .check(matches(isDisplayed()));
    }

    private static Matcher<RecyclerView.ViewHolder> withTitle(final String title) {
        Checks.checkNotNull(title);
        return new BoundedMatcher<RecyclerView.ViewHolder, DepartmentsAdapter.DepartmentItemViewHolder>(DepartmentsAdapter.DepartmentItemViewHolder.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("view holder with title : " + title);
            }

            @Override
            protected boolean matchesSafely(DepartmentsAdapter.DepartmentItemViewHolder item) {
                return item.mItem.getName().equalsIgnoreCase(title);
            }
        };
    }
}
