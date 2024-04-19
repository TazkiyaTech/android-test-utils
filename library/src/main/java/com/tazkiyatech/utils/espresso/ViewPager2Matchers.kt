package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.viewpager2.widget.ViewPager2
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object ViewPager2Matchers {

    /**
     * Creates a matcher that matches a [ViewPager2] which has the given page index as its currently selected page.
     *
     * Usage example:
     * ```
     * onView(withId(R.id.viewPager)).check(matches(withCurrentItem(2)))
     * ```
     *
     * @param item The page index to match on.
     * @return A matcher that matches a [ViewPager2] which has the given page index as its currently selected page.
     */
    fun withCurrentItem(item: Int): Matcher<View> {
        return WithCurrentItemMatcher(item)
    }

    /**
     * Creates a matcher that matches the child [View]
     * at the given position within the [ViewPager2] which has the given resource id.
     *
     * Note that it's necessary to scroll the [ViewPager2] to the desired position or close to the desired position
     * before attempting to match the child [View] at that position.
     * Otherwise, it's likely that the [ViewPager2] will not have rendered the child [View] at that position.
     *
     * Usage example:
     * ```
     * onView(
     *     withPositionInViewPager2(R.id.myViewPager, position)
     * ).check(
     *     matches(hasDescendant(withId(R.id.someView)))
     * )
     * ```
     *
     * @param viewPagerId The resource id of the [ViewPager2] which contains the child [View] to match on.
     * @param position The page index of the child [View] to match on.
     * @return A matcher that matches the child [View]
     * at the given position within the [ViewPager2] which has the given resource id.
     */
    fun withPositionInViewPager2(@IdRes viewPagerId: Int, position: Int): Matcher<View> {
        return WithPositionInViewPager2Matcher(viewPagerId, position)
    }

    private class WithCurrentItemMatcher(private val item: Int) :
        BoundedMatcher<View, ViewPager2>(ViewPager2::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("ViewPager2 with current item $item")
        }

        override fun matchesSafely(viewPager: ViewPager2): Boolean {
            return viewPager.currentItem == item
        }
    }

    private class WithPositionInViewPager2Matcher(
        @IdRes private val viewPagerId: Int,
        private val position: Int
    ) : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("with position $position in ViewPager2 which has id $viewPagerId")
        }

        override fun matchesSafely(item: View): Boolean {
            val parent = item.parent as? RecyclerView
                ?: return false

            val grandParent = parent.parent as? ViewPager2
                ?: return false

            if (grandParent.id != viewPagerId)
                return false

            val viewHolder = parent.findViewHolderForAdapterPosition(position)
                ?: return false

            return item == viewHolder.itemView
        }
    }
}
