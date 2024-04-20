package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import org.hamcrest.Matcher

/**
 * Espresso actions for interacting with a [ViewPager2].
 *
 * This class is a copy of the
 * [ViewPagerActions.java](https://github.com/android/android-test/blob/master/espresso/contrib/java/androidx/test/espresso/contrib/ViewPagerActions.java)
 * class with a small number of modifications applied to it to make it work for [ViewPager2] instead of
 * [ViewPager](https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager).
 *
 * I have created an issue in the [ViewPager2 IssueTracker space](https://issuetracker.google.com/issues/207785217)
 * requesting for this class to be added in a `viewpager2-testing` library within the
 * [androidx.viewpager2](https://maven.google.com/web/index.html#androidx.viewpager2) group.
 */
object ViewPager2Actions {

    private const val DEFAULT_SMOOTH_SCROLL = false

    /**
     * Creates a [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the right by one page.
     *
     * Usage example:
     * ```
     * onView(withId(R.id.myViewPager)).perform(scrollRight())
     * ```
     *
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately.
     * @return A [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the right by one page.
     */
    @JvmOverloads
    fun scrollRight(smoothScroll: Boolean = DEFAULT_SMOOTH_SCROLL): ViewAction {
        return object : ViewPagerScrollAction() {

            override fun getDescription(): String {
                return "ViewPager2 move one page to the right"
            }

            override fun performScroll(viewPager: ViewPager2) {
                val current = viewPager.currentItem
                viewPager.setCurrentItem(current + 1, smoothScroll)
            }
        }
    }

    /**
     * Creates a [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the left by one page.
     *
     * Usage example:
     * ```
     * onView(withId(R.id.myViewPager)).perform(scrollLeft())
     * ```
     *
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately.
     * @return A [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the left by one page.
     */
    @JvmOverloads
    fun scrollLeft(smoothScroll: Boolean = DEFAULT_SMOOTH_SCROLL): ViewAction {
        return object : ViewPagerScrollAction() {

            override fun getDescription(): String {
                return "ViewPager2 move one page to the left"
            }

            override fun performScroll(viewPager: ViewPager2) {
                val current = viewPager.currentItem
                viewPager.setCurrentItem(current - 1, smoothScroll)
            }
        }
    }

    /**
     * Creates a [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the last page.
     *
     * Usage example:
     * ```
     * onView(withId(R.id.myViewPager)).perform(scrollToLast())
     * ```
     *
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately.
     * @return A [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the last page.
     */
    @JvmOverloads
    fun scrollToLast(smoothScroll: Boolean = DEFAULT_SMOOTH_SCROLL): ViewAction {
        return object : ViewPagerScrollAction() {

            override fun getDescription(): String {
                return "ViewPager2 move to last page"
            }

            override fun performScroll(viewPager: ViewPager2) {
                val size = viewPager.adapter!!.itemCount
                if (size > 0) {
                    viewPager.setCurrentItem(size - 1, smoothScroll)
                }
            }
        }
    }

    /**
     * Creates a [ViewAction] that moves the [ViewPager2] to the first page.
     *
     * Usage example:
     * ```
     * onView(withId(R.id.myViewPager)).perform(scrollToFirst())
     * ```
     *
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately.
     * @return A [ViewAction] that moves the [ViewPager2] to the first page.
     */
    @JvmOverloads
    fun scrollToFirst(smoothScroll: Boolean = DEFAULT_SMOOTH_SCROLL): ViewAction {
        return object : ViewPagerScrollAction() {
            override fun getDescription(): String {
                return "ViewPager2 move to first page"
            }

            override fun performScroll(viewPager: ViewPager2) {
                val size = viewPager.adapter!!.itemCount
                if (size > 0) {
                    viewPager.setCurrentItem(0, smoothScroll)
                }
            }
        }
    }

    /**
     * Creates a [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the specified page.
     *
     * Usage example:
     * ```
     * onView(withId(R.id.myViewPager)).perform(scrollToPage(2))
     * ```
     *
     * @param page The index of the page to scroll to.
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately.
     * @return A [ViewAction] that moves the [ViewPager2] which is the subject of this [ViewAction] to the specified page.
     */
    @JvmOverloads
    fun scrollToPage(page: Int, smoothScroll: Boolean = DEFAULT_SMOOTH_SCROLL): ViewAction {
        return object : ViewPagerScrollAction() {

            override fun getDescription(): String {
                return "ViewPager2 move to page"
            }

            override fun performScroll(viewPager: ViewPager2) {
                viewPager.setCurrentItem(page, smoothScroll)
            }
        }
    }

    /**
     * [ViewPager2] listener that serves as Espresso's [IdlingResource] and notifies the
     * registered callback when the [ViewPager2] gets to the [ViewPager2.SCROLL_STATE_IDLE] state.
     */
    private class CustomViewPager2Listener : OnPageChangeCallback(), IdlingResource {

        private var currentState = ViewPager2.SCROLL_STATE_IDLE
        private var resourceCallback: ResourceCallback? = null
        var needsIdle = false

        override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
            this.resourceCallback = resourceCallback
        }

        override fun getName(): String {
            return "ViewPager2 listener"
        }

        override fun isIdleNow(): Boolean {
            return if (!needsIdle) {
                true
            } else {
                currentState == ViewPager2.SCROLL_STATE_IDLE
            }
        }

        override fun onPageSelected(position: Int) {
            if (currentState == ViewPager2.SCROLL_STATE_IDLE) {
                resourceCallback?.onTransitionToIdle()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            currentState = state
            if (currentState == ViewPager2.SCROLL_STATE_IDLE) {
                resourceCallback?.onTransitionToIdle()
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // nothing to do
        }
    }

    private abstract class ViewPagerScrollAction : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isDisplayed()
        }

        override fun perform(uiController: UiController, view: View) {
            val viewPager = view as ViewPager2

            // Add a custom tracker listener
            val customViewPager2Listener = CustomViewPager2Listener()
            viewPager.registerOnPageChangeCallback(customViewPager2Listener)

            // Note that we're running the following block in a try-finally construct.
            // This is needed since some of the actions are going to throw (expected) exceptions.
            // If that happens, we still need to clean up after ourselves
            // to leave the system (Espresso) in a good state.
            try {
                // Register our listener as idling resource so that Espresso waits until the
                // wrapped action results in the ViewPager2 getting to the SCROLL_STATE_IDLE state
                IdlingRegistry.getInstance().register(customViewPager2Listener)
                uiController.loopMainThreadUntilIdle()
                performScroll(viewPager)
                uiController.loopMainThreadUntilIdle()
                customViewPager2Listener.needsIdle = true
                uiController.loopMainThreadUntilIdle()
                customViewPager2Listener.needsIdle = false
            } finally {
                // Unregister our idling resource
                IdlingRegistry.getInstance().unregister(customViewPager2Listener)
                // And remove our tracker listener from ViewPager2
                viewPager.unregisterOnPageChangeCallback(customViewPager2Listener)
            }
        }

        protected abstract fun performScroll(viewPager: ViewPager2)
    }
}
