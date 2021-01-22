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
     * @return a [Matcher] that matches a [View] if it has a descendant in its view hierarchy
     * which has the given id and text.
     */
    fun hasDescendantWithIdAndText(@IdRes id: Int, text: String): Matcher<View> {
        return hasDescendant(allOf(withId(id), withText(text)))
    }

    /**
     * @return a [Matcher] that matches a [View] if it has a descendant in its view hierarchy
     * which has the given id and substring.
     */
    fun hasDescendantWithIdAndSubstring(@IdRes id: Int, @StringRes stringResourceId: Int): Matcher<View> {
        return hasDescendant(allOf(withId(id), withSubstring(stringResourceId)))
    }

    /**
     * Provides a nicer alternative to calling `not(isDisplayed())`.
     *
     * @see androidx.test.espresso.matcher.ViewMatchers.isDisplayed
     */
    fun isNotDisplayed(): Matcher<View> {
        return not(isDisplayed())
    }
}