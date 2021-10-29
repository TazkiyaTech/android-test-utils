package com.tazkiyatech.utils.uiautomator

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Test

class UiDeviceExtensionsTest {

    private val uiDevice: UiDevice
        get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun waitOnLauncher() {
        // When.
        uiDevice.pressHome()

        // Then.
        uiDevice.waitOnLauncher()
    }
}