package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

object TabViewMatchers {

    /**
     * Creates a matcher that matches a [TabLayout.TabView] which is in the selected state.
     *
     * Usage example:
     * ```
     * onView(withTabText("Some text")).check(matches(isSelectedTab()))
     * ```
     *
     * @return A matcher that matches a [TabLayout.TabView] which is in the selected state.
     */
    fun isSelectedTab(): Matcher<View> {
        return IsSelectedTabMatcher()
    }

    /**
     * Creates a matcher that matches a [TabLayout.TabView] which has the given text.
     *
     * Usage example:
     * ```
     * onView(withTabText("Some text")).check(matches(isSelectedTab()))
     * ```
     *
     * @param text The text to match on.
     * @return A matcher that matches a [TabLayout.TabView] which has the given text.
     */
    fun withTabText(text: String): Matcher<View> {
        return WithTabTextMatcher(text)
    }

    private class IsSelectedTabMatcher :
        BoundedMatcher<View, TabLayout.TabView>(TabLayout.TabView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("TabView is selected")
        }

        override fun matchesSafely(tabView: TabLayout.TabView): Boolean {
            return tabView.tab?.isSelected == true
        }
    }

    private class WithTabTextMatcher(private val text: String) :
        BoundedMatcher<View, TabLayout.TabView>(TabLayout.TabView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("TabView with text $text")
        }

        override fun matchesSafely(tabView: TabLayout.TabView): Boolean {
            return text == tabView.tab?.text
        }
    }
}
