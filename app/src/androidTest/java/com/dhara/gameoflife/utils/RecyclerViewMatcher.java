package com.dhara.gameoflife.utils;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.intent.Checks.checkNotNull;

public class RecyclerViewMatcher {
    public static Matcher<Object> withTag(Object tagValue) {
        return withTagValue(Matchers.equalTo(tagValue));
    }

    private static Matcher<Object> withTagValue(final Matcher<Object> tagValue) {
        checkNotNull(tagValue);
        return new BoundedMatcher<Object, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with tag: ");
                tagValue.describeTo(description);
            }

            @Override
            public boolean matchesSafely(TextView textView) {
                return tagValue.matches(textView.getTag());
            }
        };
    }
}
