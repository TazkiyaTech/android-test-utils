package com.tazkiyatech.utils.espresso;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.test.espresso.*;
import androidx.viewpager2.widget.ViewPager2;

import org.hamcrest.Matcher;

/**
 * Espresso actions for interacting with a {@link ViewPager2}.
 * <p>
 * The implementation of this class has been copied over from
 * <a href="https://github.com/android/android-test/blob/master/espresso/contrib/java/androidx/test/espresso/contrib/ViewPagerActions.java">ViewPagerActions.java</a>
 * with a small number of modifications to make it work for
 * {@link ViewPager2} instead of
 * <a href="https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager">ViewPager</a>.
 * <p>
 * I have created an issue in the ViewPager2 IssueTracker space
 * <a href="https://issuetracker.google.com/issues/207785217">here</a>
 * requesting for this class to be added in a {@code viewpager2-testing} library within the
 * <a href="https://maven.google.com/web/index.html#androidx.viewpager2">androidx.viewpager2</a>
 * group.
 */
public final class ViewPager2Actions {

    private static final boolean DEFAULT_SMOOTH_SCROLL = false;

    private ViewPager2Actions() {
        // forbid instantiation
    }

    /**
     * Moves {@link ViewPager2} to the right by one page.
     */
    public static ViewAction scrollRight() {
        return scrollRight(DEFAULT_SMOOTH_SCROLL);
    }

    /**
     * Moves {@link ViewPager2} to the right by one page.
     */
    public static ViewAction scrollRight(final boolean smoothScroll) {
        return new ViewPagerScrollAction() {
            @Override
            public String getDescription() {
                return "ViewPager2 move one page to the right";
            }

            @Override
            protected void performScroll(ViewPager2 viewPager) {
                int current = viewPager.getCurrentItem();
                viewPager.setCurrentItem(current + 1, smoothScroll);
            }
        };
    }

    /**
     * Moves {@link ViewPager2} to the left be one page.
     */
    public static ViewAction scrollLeft() {
        return scrollLeft(DEFAULT_SMOOTH_SCROLL);
    }

    /**
     * Moves {@link ViewPager2} to the left by one page.
     */
    public static ViewAction scrollLeft(final boolean smoothScroll) {
        return new ViewPagerScrollAction() {
            @Override
            public String getDescription() {
                return "ViewPager2 move one page to the left";
            }

            @Override
            protected void performScroll(ViewPager2 viewPager) {
                int current = viewPager.getCurrentItem();
                viewPager.setCurrentItem(current - 1, smoothScroll);
            }
        };
    }

    /**
     * Moves {@link ViewPager2} to the last page.
     */
    public static ViewAction scrollToLast() {
        return scrollToLast(DEFAULT_SMOOTH_SCROLL);
    }

    /**
     * Moves {@link ViewPager2} to the last page.
     */
    public static ViewAction scrollToLast(final boolean smoothScroll) {
        return new ViewPagerScrollAction() {
            @Override
            public String getDescription() {
                return "ViewPager2 move to last page";
            }

            @Override
            protected void performScroll(ViewPager2 viewPager) {
                int size = viewPager.getAdapter().getItemCount();
                if (size > 0) {
                    viewPager.setCurrentItem(size - 1, smoothScroll);
                }
            }
        };
    }

    /**
     * Moves {@link ViewPager2} to the first page.
     */
    public static ViewAction scrollToFirst() {
        return scrollToFirst(DEFAULT_SMOOTH_SCROLL);
    }

    /**
     * Moves {@link ViewPager2} to the first page.
     */
    public static ViewAction scrollToFirst(final boolean smoothScroll) {
        return new ViewPagerScrollAction() {
            @Override
            public String getDescription() {
                return "ViewPager2 move to first page";
            }

            @Override
            protected void performScroll(ViewPager2 viewPager) {
                int size = viewPager.getAdapter().getItemCount();
                if (size > 0) {
                    viewPager.setCurrentItem(0, smoothScroll);
                }
            }
        };
    }

    /**
     * Moves {@link ViewPager2} to a specific page.
     */
    public static ViewAction scrollToPage(int page) {
        return scrollToPage(page, DEFAULT_SMOOTH_SCROLL);
    }

    /**
     * Moves {@link ViewPager2} to specific page.
     */
    public static ViewAction scrollToPage(final int page, final boolean smoothScroll) {
        return new ViewPagerScrollAction() {
            @Override
            public String getDescription() {
                return "ViewPager2 move to page";
            }

            @Override
            protected void performScroll(ViewPager2 viewPager) {
                viewPager.setCurrentItem(page, smoothScroll);
            }
        };
    }

    /**
     * {@link ViewPager2} listener that serves as Espresso's {@link IdlingResource} and notifies the
     * registered callback when the {@link ViewPager2} gets to SCROLL_STATE_IDLE state.
     */
    private static final class CustomViewPager2Listener extends ViewPager2.OnPageChangeCallback
            implements IdlingResource {
        private int mCurrState = ViewPager2.SCROLL_STATE_IDLE;

        @Nullable
        private IdlingResource.ResourceCallback mCallback;

        private boolean mNeedsIdle = false;

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            mCallback = resourceCallback;
        }

        @Override
        public String getName() {
            return "ViewPager2 listener";
        }

        @Override
        public boolean isIdleNow() {
            if (!mNeedsIdle) {
                return true;
            } else {
                return mCurrState == ViewPager2.SCROLL_STATE_IDLE;
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mCurrState == ViewPager2.SCROLL_STATE_IDLE) {
                if (mCallback != null) {
                    mCallback.onTransitionToIdle();
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mCurrState = state;
            if (mCurrState == ViewPager2.SCROLL_STATE_IDLE) {
                if (mCallback != null) {
                    mCallback.onTransitionToIdle();
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
    }

    private abstract static class ViewPagerScrollAction implements ViewAction {

        @Override
        public final Matcher<View> getConstraints() {
            return isDisplayed();
        }

        @Override
        public final void perform(UiController uiController, View view) {
            final ViewPager2 viewPager = (ViewPager2) view;

            // Add a custom tracker listener
            final CustomViewPager2Listener customListener = new CustomViewPager2Listener();
            viewPager.registerOnPageChangeCallback(customListener);

            // Note that we're running the following block in a try-finally construct.
            // This is needed since some of the actions are going to throw (expected) exceptions.
            // If that happens, we still need to clean up after ourselves
            // to leave the system (Espresso) in a good state.
            try {
                // Register our listener as idling resource so that Espresso waits until the
                // wrapped action results in the ViewPager2 getting to the SCROLL_STATE_IDLE state
                IdlingRegistry.getInstance().register(customListener);

                uiController.loopMainThreadUntilIdle();

                performScroll((ViewPager2) view);

                uiController.loopMainThreadUntilIdle();

                customListener.mNeedsIdle = true;
                uiController.loopMainThreadUntilIdle();
                customListener.mNeedsIdle = false;
            } finally {
                // Unregister our idling resource
                IdlingRegistry.getInstance().unregister(customListener);
                // And remove our tracker listener from ViewPager2
                viewPager.unregisterOnPageChangeCallback(customListener);
            }
        }

        protected abstract void performScroll(ViewPager2 viewPager);
    }
}
