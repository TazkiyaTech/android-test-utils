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
     * Returns a [Matcher] that matches a [View] if it has a descendant in its view hierarchy
     * which has the given id and text. For example:
     *
     * ```
     * onView(
     *     withId(R.id.viewGroup)
     * ).checkMatches(
     *     hasDescendantWithIdAndText(R.id.textView, "Hello")
     * )
     * ```
     */
    fun hasDescendantWithIdAndText(@IdRes id: Int, text: String): Matcher<View> {
        return hasDescendant(allOf(withId(id), withText(text)))
    }

    /**
     * Returns a [Matcher] that matches a [View] if it has a descendant in its view hierarchy
     * which has the given id and substring. For example:
     *
     * ```
     * onView(
     *     withId(R.id.viewGroup)
     * ).checkMatches(
     *     hasDescendantWithIdAndSubstring(R.id.textView, R.string.hello)
     * )
     * ```
     */
    fun hasDescendantWithIdAndSubstring(@IdRes id: Int, @StringRes stringResourceId: Int): Matcher<View> {
        return hasDescendant(allOf(withId(id), withSubstring(stringResourceId)))
    }

    /**
     * Provides a nicer alternative to calling `not(isDisplayed())`. For example:
     *
     * ```
     * onView(withId(R.id.button)).checkMatches(notDisplayed())
     * ```
     *
     * @see androidx.test.espresso.matcher.ViewMatchers.isDisplayed
     */
    fun notDisplayed(): Matcher<View> {
        return not(isDisplayed())
    }
}
