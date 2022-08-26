package com.tazkiyatech.utils.espresso

import android.R
import android.content.Context
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertThat
import org.junit.Test

class TextViewMatchersTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun withSubstring() {
        // Given.
        val textView = TextView(context)

        // When.
        textView.text = context.getString(R.string.copy) + " " + context.getString(R.string.cut)

        // Then.
        assertThat(textView, TextViewMatchers.withSubstring(R.string.cut))
    }
}
