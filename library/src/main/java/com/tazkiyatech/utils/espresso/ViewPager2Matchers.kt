package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.viewpager2.widget.ViewPager2
import org.hamcrest.Description
import org.hamcrest.Matcher

object ViewPager2Matchers {

    /**
     * @return an instance of [WithCurrentItemMatcher] created with the given [item] parameter.
     */
    fun withCurrentItem(item: Int): Matcher<View> {
        return WithCurrentItemMatcher(item)
    }

    /**
     * A matcher that matches a [ViewPager2] which has the given page index as its currently selected page.
     */
    private class WithCurrentItemMatcher(private val item: Int) :
            BoundedMatcher<View, ViewPager2>(ViewPager2::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("ViewPager2 with current item $item")
        }

        override fun matchesSafely(viewPager: ViewPager2): Boolean {
            return viewPager.currentItem == item
        }
    }

}