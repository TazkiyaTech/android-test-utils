package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object RecyclerViewMatchers {

    /**
     * @param size The number of items in the adapter of the [RecyclerView] to match on.
     * @return A matcher that matches a [RecyclerView] which contains an adapter with the given number of items.
     */
    fun hasAdapterSize(size: Int): Matcher<View> {
        return HasAdapterSizeMatcher(size)
    }

    /**
     * Note that it's necessary to scroll the [RecyclerView] to the desired position or close to the desired position
     * before attempting to match the child [View] at that position.
     * Otherwise, it's likely that the [RecyclerView] will not have rendered the child [View] at that position.
     *
     * @param recyclerViewId The resource id of the [RecyclerView] which contains the child [View] to match on.
     * @param position The index of the child [View] to match on.
     * @return A matcher that matches the child [View] at the given position
     * within the [RecyclerView] which has the given resource id.
     */
    fun withPositionInRecyclerView(@IdRes recyclerViewId: Int, position: Int): Matcher<View> {
        return WithPositionInRecyclerViewMatcher(recyclerViewId, position)
    }

    private class HasAdapterSizeMatcher(private val size: Int) :
        BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("RecyclerView containing adapter with item count $size")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val adapter = recyclerView.adapter
                ?: return false // has no adapter

            return adapter.itemCount == size
        }
    }

    private class WithPositionInRecyclerViewMatcher(
        @IdRes private val recyclerViewId: Int,
        private val position: Int
    ) : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("with position $position in RecyclerView which has id $recyclerViewId")
        }

        override fun matchesSafely(item: View): Boolean {
            val parent = item.parent as? RecyclerView
                ?: return false

            if (parent.id != recyclerViewId)
                return false

            val viewHolder = parent.findViewHolderForAdapterPosition(position)
                ?: return false

            return item == viewHolder.itemView
        }
    }
}