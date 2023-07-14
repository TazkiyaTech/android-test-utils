package com.tazkiyatech.utils.espresso

import android.content.Context
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import com.tazkiyatech.utils.test.R
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TextViewMatchersTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun withSubstring() {
        // Given.
        val textView = TextView(context)

        // When.
        textView.text = context.getString(R.string.lorem_ipsum_long_version)

        // Then.
        assertThat(textView, TextViewMatchers.withSubstring(R.string.lorem_ipsum_short_version))
    }
}
