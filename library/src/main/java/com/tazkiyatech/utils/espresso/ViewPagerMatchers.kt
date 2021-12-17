package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.viewpager.widget.ViewPager
import org.hamcrest.Description
import org.hamcrest.Matcher

object ViewPagerMatchers {

    /**
     * @param item The page index to match on.
     * @return A matcher that matches a [ViewPager] which has the given page index as its currently selected page.
     */
    fun withCurrentItem(item: Int): Matcher<View> {
        return WithCurrentItemMatcher(item)
    }

    private class WithCurrentItemMatcher(private val item: Int) :
        BoundedMatcher<View, ViewPager>(ViewPager::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("ViewPager with current item $item")
        }

        override fun matchesSafely(viewPager: ViewPager): Boolean {
            return viewPager.currentItem == item
        }
    }

}