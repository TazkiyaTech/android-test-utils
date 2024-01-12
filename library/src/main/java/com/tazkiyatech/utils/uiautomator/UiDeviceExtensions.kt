package com.tazkiyatech.utils.uiautomator

import android.os.Build
import androidx.test.uiautomator.*
import org.junit.Assert.fail
import java.util.concurrent.TimeoutException

private const val TIMEOUT_MILLIS: Long = 10_000L
private const val TIMEOUT_SECONDS: Double = TIMEOUT_MILLIS / 1000.0

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
 * because that method returns `"com.android.settings"` when run in an Android 11+ device
 * due to package visibility changes introduced in Android 11.
 *
 * This method assumes that the Android 11+ device on which the tests are being run
 * has not changed its default launcher app from the one that the device shipped with.
 *
 * The following issue describes the problem with the [UiDevice.getLauncherPackageName] method
 * in greater detail: [https://github.com/android/android-test/issues/1183](https://github.com/android/android-test/issues/1183)
 *
 * @return the package name of the device's default launcher (a.k.a. home) app.
 */
private val UiDevice.launcherPackage: String
    get() {
        val launcherPackageName = launcherPackageName

        if (launcherPackageName == "com.android.settings" && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Build.MANUFACTURER.equals("google", ignoreCase = true)) {
                return "com.google.android.apps.nexuslauncher"
            } else if (Build.MANUFACTURER.equals("motorola", ignoreCase = true)) {
                return "com.motorola.launcher3"
            }
        }

        return launcherPackageName
    }

/**
 * Calls into the [UiDevice.wait] function with a `timeout` value of [TIMEOUT_MILLIS].
 *
 * @param condition The [SearchCondition] to evaluate.
 * @return The final result returned by the [SearchCondition].
 * @throws TimeoutException if the [SearchCondition] is not met.
 */
@Throws(TimeoutException::class)
fun <R> UiDevice.wait(condition: SearchCondition<R>): R {
    val result = wait(condition, TIMEOUT_MILLIS)

    @Suppress("KotlinConstantConditions")
    if (result == null || result == false) {
        throw TimeoutException("Waited on the search condition for $TIMEOUT_SECONDS seconds but it was not met.")
    } else {
        return result
    }
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
