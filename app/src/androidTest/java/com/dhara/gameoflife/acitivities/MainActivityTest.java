package com.dhara.gameoflife.acitivities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dhara.gameoflife.R;
import com.dhara.gameoflife.activities.MainActivity;
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
import static com.dhara.gameoflife.utils.TestUtils.withRecyclerView;

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
    public void testSelfPatternGeneration() throws InterruptedException{
        onView(withId(R.id.recycler_view_states)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(500);

        onView(withRecyclerView(R.id.recycler_view_states)
                .atPositionOnView(0, R.id.txt_cell_state))
                .check(matches(RecyclerViewMatcher.withTag(true)));

        onView(withRecyclerView(R.id.recycler_view_states)
                .atPositionOnView(2, R.id.txt_cell_state))
                .check(matches(RecyclerViewMatcher.withTag(false)));
    }

    @Test
    public void testOnStartClicked() throws InterruptedException{
        onView(withId(R.id.btn_start)).perform(click());
        onView(withId(R.id.lnr_temp)).check(isVisible());

        onView(withId(R.id.recycler_view_states)).perform(
                RecyclerViewActions.actionOnItemAtPosition(3, click()));

        Thread.sleep(500);

        onView(withRecyclerView(R.id.recycler_view_states)
                .atPositionOnView(3, R.id.txt_cell_state))
                .check(matches(RecyclerViewMatcher.withTag(false)));

        Thread.sleep(1000);
    }

    @Test
    public void testOnStopClicked() throws InterruptedException {
        onView(withId(R.id.btn_stop)).perform(click());
        onView(withId(R.id.lnr_temp)).check(isGone());
        Thread.sleep(1000);
    }

    @Test
    public void testOnDestroy() {
        mActivityRule.getActivity().finish();
    }
}
