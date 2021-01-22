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
     * @return an instance of [HasAdapterSizeMatcher] created with the given [size] parameter.
     */
    fun hasAdapterSize(size: Int): Matcher<View> {
        return HasAdapterSizeMatcher(size)
    }

    /**
     * @return an instance of [WithPositionInRecyclerViewMatcher] created with the given parameters.
     */
    fun withPositionInRecyclerView(@IdRes recyclerViewId: Int, position: Int): Matcher<View> {
        return WithPositionInRecyclerViewMatcher(recyclerViewId, position)
    }

    /**
     * A matcher that matches a [RecyclerView] which contains an adapter with the given number of items.
     */
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

    /**
     * A matcher that matches the child [View] at the given position
     * within the [RecyclerView] which has the given resource id.
     *
     * Note that it's necessary to scroll the [RecyclerView] to the desired position
     * before attempting to match the child [View] at that position.
     */
    private class WithPositionInRecyclerViewMatcher(@IdRes private val recyclerViewId: Int,
                                                    private val position: Int) : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("with position $position in RecyclerView which has id $recyclerViewId")
        }

        override fun matchesSafely(item: View): Boolean {
            val parent = item.parent as? RecyclerView
                ?: return false

            if (parent.id != recyclerViewId)
                return false

            val viewHolder: RecyclerView.ViewHolder = parent.findViewHolderForAdapterPosition(position)
                ?: return false // has no item on such position

            return item == viewHolder.itemView
        }
    }
}