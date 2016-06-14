package com.dhara.gameoflife.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.intent.Checks.checkNotNull;

public class RecyclerViewMatcher {
    private static Bitmap mBitmap;
    private static Canvas mCanvas;
    private static Rect mBounds;
    private static int COLOR_BLACK = android.R.color.black;
    private static int COLOR_DARKER_GRAY = android.R.color.darker_gray;

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static Matcher<View> withColor(int color) {
        return withSelectedBackground(Matchers.equalTo(color));
    }

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

    private static Matcher<View> withSelectedBackground(final Matcher<Integer> bgMatcher) {
        checkNotNull(bgMatcher);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with background color: ");

                String colorName;

                if (bgMatcher.matches(COLOR_DARKER_GRAY)) colorName = "DARKER_GRAY";
                else if (bgMatcher.matches(COLOR_BLACK)) colorName = "BLACK";
                else colorName = "TRANSPARENT";
                description.appendText("View color must be " + colorName);

                bgMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(TextView textView) {
                ColorDrawable viewBackground = (ColorDrawable)textView.getBackground();

                int colorId;

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    initIfNeeded();

                    // If the ColorDrawable makes use of its bounds in the draw method,
                    // we may not be able to get the color we want. This is not the usual
                    // case before Ice Cream Sandwich (4.0.1 r1).
                    // Yet, we change the bounds temporarily, just to be sure that we are
                    // successful.
                    mBounds.set(viewBackground.getBounds()); // Save the original bounds.
                    viewBackground.setBounds(0, 0, 1, 1); // Change the bounds.

                    viewBackground.draw(mCanvas);
                    colorId = mBitmap.getPixel(0, 0);
                    viewBackground.setBounds(mBounds); // Restore the original bounds.
                } else {
                    colorId = viewBackground.getColor();
                }
                return bgMatcher.matches(colorId);
            }
        };
    }

    private static void initIfNeeded() {
        if(mBitmap == null) {
            mBitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mBounds = new Rect();
        }
    }
}
