package com.tazkiyatech.utils.espresso

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.tabs.TabLayout
import com.tazkiyatech.utils.espresso.TabViewMatchers.isSelectedTab
import com.tazkiyatech.utils.espresso.TabViewMatchers.withTabText
import com.tazkiyatech.utils.test.R
import org.hamcrest.Matchers.not
import org.junit.Assert.assertThat
import org.junit.Test

class TabViewMatchersTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun isSelectedTab_when_TabLayout_has_multiple_tabs() {
        // Given.
        val tabLayout = TabLayout(ContextThemeWrapper(context, R.style.Theme_AppCompat))

        val tab1 = tabLayout.newTab()
        val tab2 = tabLayout.newTab()

        // When.
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)

        // Then.
        assertThat(tab1.view, isSelectedTab())
        assertThat(tab2.view, not(isSelectedTab()))

        // And When.
        tabLayout.selectTab(tab2)

        // Then.
        assertThat(tab1.view, not(isSelectedTab()))
        assertThat(tab2.view, isSelectedTab())
    }

    @Test
    fun withTabText_when_Tab_has_text() {
        // Given.
        val tabLayout = TabLayout(ContextThemeWrapper(context, R.style.Theme_AppCompat))
        val tab = tabLayout.newTab()

        // When.
        tab.text = "Some Text"

        // Then.
        assertThat(tab.view, withTabText("Some Text"))
    }
}
