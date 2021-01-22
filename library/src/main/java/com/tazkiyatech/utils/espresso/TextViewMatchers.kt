package com.tazkiyatech.utils.espresso

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

object TextViewMatchers {

    /**
     * @param resourceId the identifier of the [String] resource
     * which the [android.widget.TextView] is expected to contain.
     * @return a matcher that matches a descendant of [android.widget.TextView]
     * which fully or partially contains the string associated with the given resource id.
     */
    fun withSubstring(@StringRes resourceId: Int): Matcher<View> {
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        val substring = applicationContext.getString(resourceId)
        return ViewMatchers.withSubstring(substring)
    }

    /**
     * Matches a [TextView] if its text is equal to the string associated with the given resource id
     * when all HTML tags are ignored from both items in the comparison.
     */
    fun withHtmlText(@StringRes resourceId: Int): BoundedMatcher<View, TextView> {
        return WithHtmlText(resourceId)
    }

    private class WithHtmlText(@StringRes val resourceId: Int) :
        BoundedMatcher<View, TextView>(TextView::class.java) {

        private var expectedText: String? = null
        private var resourceName: String? = null

        override fun describeTo(description: Description) {
            description.appendText("with text from resource id: ").appendValue(resourceId)
            resourceName?.let {
                description.appendText("[").appendText(it).appendText("]")
            }
            expectedText?.let {
                description.appendText(" value: ").appendText(it)
            }
        }

        override fun matchesSafely(textView: TextView): Boolean {
            val source = textView.resources.getString(resourceId)

            expectedText = HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            resourceName = textView.resources.getResourceEntryName(resourceId)

            return textView.text.toString() == expectedText
        }
    }
}