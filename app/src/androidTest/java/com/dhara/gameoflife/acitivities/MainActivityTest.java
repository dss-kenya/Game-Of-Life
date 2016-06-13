package com.dhara.gameoflife.acitivities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dhara.gameoflife.activities.MainActivity;
import com.dhara.gameoflife.R;
import com.dhara.gameoflife.utils.RecyclerViewMatcher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.dhara.gameoflife.utils.ExtraAssertions.isGone;
import static com.dhara.gameoflife.utils.ExtraAssertions.isVisible;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testScreenVisibility() {
        onView(withId(R.id.lnr_temp)).check(isGone());
        onView(withId(R.id.lnr_buttons)).check(isVisible());
        onView(withId(R.id.recycler_view_states)).check(matches(isDisplayed()));
    }

    @Test
    public void testSelfPatternGeneration() {
        onView(withId(R.id.recycler_view_states)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recycler_view_states))
                .check(matches(RecyclerViewMatcher.atPosition(0,
                        RecyclerViewMatcher.withColor(android.R.color.black))));

        onView(withId(R.id.recycler_view_states))
                .check(matches(RecyclerViewMatcher.atPosition(2,
                        RecyclerViewMatcher.withColor(android.R.color.darker_gray))));
    }

    @Test
    public void testOnStartClicked() {
        onView(withId(R.id.btn_start)).perform(click());
        onView(withId(R.id.lnr_temp)).check(isVisible());
    }

    @Test
    public void testOnStopClicked() {
        onView(withId(R.id.btn_stop)).perform(click());
        onView(withId(R.id.lnr_temp)).check(isGone());
    }
}
