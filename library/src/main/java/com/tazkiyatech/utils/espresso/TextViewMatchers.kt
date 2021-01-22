package com.tazkiyatech.utils.espresso

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
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
}