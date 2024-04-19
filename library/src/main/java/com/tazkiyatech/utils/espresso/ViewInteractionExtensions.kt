package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * Checks that the view which is the subject of this [ViewInteraction]
 * exists in the view hierarchy and is matched by all of the given view matchers.
 *
 * This function makes Espresso tests easier to read and write by combining a series of calls into one.
 * It offers a nicer alternative to writing the following:
 * ```
 * onView(withId(R.id.someView)).check(matches(allOf(isDisplayed(), withText("Some text"))))
 * ```
 * Instead, you can write:
 * ```
 * onView(withId(R.id.someView)).checkMatches(isDisplayed(), withText("Some text"))
 * ```
 *
 * @param matchers The array of [Matcher]s to match.
 * @return This [ViewInteraction] for further perform or verification calls.
 */
fun ViewInteraction.checkMatches(vararg matchers: Matcher<View>): ViewInteraction {
    return check(matches(allOf(*matchers)))
}

/**
 * Waits up to [timeout] milliseconds for the view which is the subject of this [ViewInteraction]
 * to match all of the given view matchers.
 *
 * This function makes Espresso tests easier to read and write by combining a series of calls into one.
 * It offers a nicer alternative to writing the following:
 * ```
 * onView(withId(R.id.someView)).perform(waitForMatch(allOf(isDisplayed(), withText("Some text"))))
 * ```
 * Instead, you can write:
 * ```
 * onView(withId(R.id.someView)).waitForMatch(isDisplayed(), withText("Some text"))
 * ```
 *
 * @param matchers The array of [Matcher]s to wait for.
 * @param timeout The length of time in milliseconds to wait for.
 * @return This [ViewInteraction] for further perform or verification calls.
 */
fun ViewInteraction.waitForMatch(
    vararg matchers: Matcher<View>,
    timeout: Long = 3000L
): ViewInteraction {
    return perform(ViewActions.waitForMatch(allOf(*matchers), timeout))
}
