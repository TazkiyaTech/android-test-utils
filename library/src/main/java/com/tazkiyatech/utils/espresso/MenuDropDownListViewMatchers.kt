package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*

/**
 * A collection of view matchers for the `MenuPopupWindow.MenuDropDownListView` class.
 *
 * The `MenuPopupWindow.MenuDropDownListView` class is the class within which a popup menu is shown.
 *
 * **See also:** [PopMenu](https://developer.android.com/reference/android/widget/PopupMenu)
 */
object MenuDropDownListViewMatchers {

    /**
     * Creates a [Matcher] that matches a [View] of type `MenuPopupWindow.MenuDropDownListView`.
     *
     * Here's a usage example:
     *
     * ```
     * onView(isMenuDropDownListView()).checkMatches(hasChildCount(3)
     * ```
     *
     * @return A [Matcher] that matches a [View] of type `MenuPopupWindow.MenuDropDownListView`.
     */
    fun isMenuDropDownListView(): Matcher<View> {
        return anyOf(
            withClassName(equalTo("android.widget.MenuPopupWindow\$MenuDropDownListView")),
            withClassName(equalTo("androidx.appcompat.widget.MenuPopupWindow\$MenuDropDownListView"))
        )
    }

    /**
     * Creates a [Matcher] that matches the child [View]
     * at the given position within a `MenuPopupWindow.MenuDropDownListView` instance.
     *
     * Here's a usage example:
     *
     * ```
     * onView(withPositionInMenuDropDownListView(1)).perform(click())
     * ```
     *
     * @param position The index of the child [View] to match on.
     * @return A [Matcher] that matches the child [View]
     * at the given position within a `MenuPopupWindow.MenuDropDownListView` instance.
     */
    fun withPositionInMenuDropDownListView(position: Int): Matcher<View> {
        return allOf(withParent(isMenuDropDownListView()), withParentIndex(position))
    }
}
