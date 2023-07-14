package com.tazkiyatech.utils.espresso

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.tabs.TabLayout
import com.tazkiyatech.utils.espresso.TabViewMatchers.withTabText
import com.tazkiyatech.utils.test.R
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.junit.Test

class TabViewMatchersTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun isSelectedTab() {
        // Given.
        val tabLayout = TabLayout(ContextThemeWrapper(context, R.style.Theme_AppCompat))

        val tab1 = tabLayout.newTab()
        val tab2 = tabLayout.newTab()

        // When.
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)

        // Then.
        assertThat(tab1.view, TabViewMatchers.isSelectedTab())
        assertThat(tab2.view, not(TabViewMatchers.isSelectedTab()))

        // And When.
        tabLayout.selectTab(tab2)

        // Then.
        assertThat(tab1.view, not(TabViewMatchers.isSelectedTab()))
        assertThat(tab2.view, TabViewMatchers.isSelectedTab())
    }

    @Test
    fun withTabText() {
        // Given.
        val tabLayout = TabLayout(ContextThemeWrapper(context, R.style.Theme_AppCompat))
        val tab = tabLayout.newTab()

        // When.
        tab.text = "Some Text"

        // Then.
        assertThat(tab.view, withTabText("Some Text"))
    }
}
