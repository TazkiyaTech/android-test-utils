package com.tazkiyatech.utils.uiautomator

import android.os.Build
import androidx.test.uiautomator.*
import org.junit.Assert.fail

private const val TIMEOUT_MILLIS: Long = 10_000L

/**
 * A [UiObject2] object which represents the overview panel that exists within the device's recent apps screen.
 */
val UiDevice.recentAppsOverviewPanel: UiObject2
    get() = findObject(recentAppsOverviewPanelSelector)

/**
 * A [BySelector] which specifies criteria for matching the overview panel that exists within the device's recent apps screen.
 */
private val UiDevice.recentAppsOverviewPanelSelector: BySelector
    get() = By.res(launcherPackage, "overview_panel")

/**
 * The package name of the device's launcher app.
 *
 * This method exists as a proxy for the [UiDevice.getLauncherPackageName] method
 * because that method returns `"com.android.settings"` (incorrectly) when run in an Android 11 emulator device.
 *
 * TODO: Remove this method and call into [UiDevice.getLauncherPackageName] directly when the following issue is resolved: [https://issuetracker.google.com/issues/178965163](https://issuetracker.google.com/issues/178965163).
 *
 * @return the package name of the device's default launcher (a.k.a. home) app.
 */
private val UiDevice.launcherPackage: String
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && productName.startsWith("sdk_gphone")) {
        // we're returning a hardcoded launcher package name because the UiDevice.getLauncherPackageName() method returns "com.android.settings" when run in an Android 11+ emulator device (which is incorrect)
        "com.google.android.apps.nexuslauncher"
    } else {
        launcherPackageName
    }

/**
 * Calls into the [UiDevice.wait] function with a `timeout` value of [TIMEOUT_MILLIS].
 */
fun <R> UiDevice.wait(condition: SearchCondition<R>): R {
    return wait(condition, TIMEOUT_MILLIS)
}

/**
 * Waits for the launcher screen to show with a `timeout` value of [TIMEOUT_MILLIS].
 */
fun UiDevice.waitOnLauncher() {
    val hasObject = wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT_MILLIS)

    if (!hasObject) {
        fail("Waited on the launcher but it did not show")
    }
}

/**
 * Waits for the recent apps screen to show with a `timeout` value of [TIMEOUT_MILLIS].
 */
fun UiDevice.waitOnRecentApps() {
    val hasObject = wait(Until.hasObject(recentAppsOverviewPanelSelector), TIMEOUT_MILLIS)

    if (!hasObject) {
        fail("Waited on recent apps but it did not show")
    }
}