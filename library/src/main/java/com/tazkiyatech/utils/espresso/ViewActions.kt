package com.tazkiyatech.utils.espresso

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.util.concurrent.TimeoutException

object ViewActions {

    /**
     * @return a [WaitForMatchAction] instance created with the given [matcher] and [timeout] parameters.
     */
    fun waitForMatch(matcher: Matcher<View>, timeout: Long = 3000L): ViewAction {
        return WaitForMatchAction(matcher, timeout)
    }

    /**
     * A [ViewAction] that waits up to [timeout] milliseconds for a [View] to match the given [Matcher].
     *
     * @param matcher the [Matcher] to wait for.
     * @param timeout the length of time in milliseconds to wait for.
     */
    private class WaitForMatchAction(private val matcher: Matcher<View>,
                                     private val timeout: Long) : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return Matchers.any(View::class.java)
        }

        override fun getDescription(): String {
            return "wait up to $timeout milliseconds for the view to match $matcher"
        }

        override fun perform(uiController: UiController, view: View) {
            val endTime = System.currentTimeMillis() + timeout

            do {
                if (matcher.matches(view)) return
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)

            throw PerformException.Builder()
                .withActionDescription(description)
                .withCause(TimeoutException("Waited $timeout milliseconds"))
                .withViewDescription(HumanReadables.describe(view))
                .build()
        }
    }

}