package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.matcher.ViewMatchers.*
import com.tazkiyatech.utils.espresso.TextViewMatchers.withSubstring
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

object ViewMatchers {

    /**
     * Creates a [Matcher] that matches a [View] if it has a descendant in its view hierarchy
     * which has the given id and text.
     *
     * Usage example:
     * ```
     * onView(
     *     withId(R.id.viewGroup)
     * ).check(
     *     matches(hasDescendantWithIdAndText(R.id.textView, "Hello"))
     * )
     * ```
     *
     * @param id: The resource ID to search for in the matched view's descendants.
     * @param text: The text to search for in the matched view's descendants.
     */
    fun hasDescendantWithIdAndText(@IdRes id: Int, text: String): Matcher<View> {
        return hasDescendant(allOf(withId(id), withText(text)))
    }

    /**
     * Creates a [Matcher] that matches a [View] if it has a descendant in its view hierarchy
     * which has the given id and substring.
     *
     * Usage example:
     * ```
     * onView(
     *     withId(R.id.viewGroup)
     * ).check(
     *     matches(hasDescendantWithIdAndSubstring(R.id.textView, R.string.hello))
     * )
     * ```
     *
     * @param id: The resource ID to search for in the matched view's descendants.
     * @param stringResourceId: The identifier of the string to search for in the matched view's descendants.
     */
    fun hasDescendantWithIdAndSubstring(@IdRes id: Int, @StringRes stringResourceId: Int): Matcher<View> {
        return hasDescendant(allOf(withId(id), withSubstring(stringResourceId)))
    }

    /**
     * Creates a [Matcher] that matches a [View] that is not currently displayed on the screen to the user.
     *
     * This function offers a nicer alternative to writing the following:
     * ```
     * onView(withId(R.id.someView)).check(matches(not(isDisplayed())))
     * ```
     * Instead, you can write:
     * ```
     * onView(withId(R.id.someView)).check(matches(notDisplayed()))
     * ```
     *
     * @return A [Matcher] that matches a [View] that is not currently displayed on the screen to the user.
     * @see androidx.test.espresso.matcher.ViewMatchers.isDisplayed
     */
    fun notDisplayed(): Matcher<View> {
        return not(isDisplayed())
    }
}
