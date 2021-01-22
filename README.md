# Android Test Utils

This library contains classes and methods designed to ease the writing of Android instrumentation tests.

## Espresso

* [RecyclerViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/RecyclerViewMatchers.kt) – Provides methods for matching on a [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView) in a UI test.
* [TextViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/TextViewMatchers.kt) – Provides methods for matching on a [TextView](https://developer.android.com/reference/android/widget/TextView) in a UI test.
* [ViewActions](library/src/main/java/com/tazkiyatech/utils/espresso/ViewActions.kt) – Provides a method for waiting on a [View](https://developer.android.com/reference/android/view/View) to match a certain condition in a UI test.
* [ViewInteractionExtensions](library/src/main/java/com/tazkiyatech/utils/espresso/ViewInteractionExtensions.kt) – Provides extension functions on the [ViewInteraction](https://developer.android.com/reference/androidx/test/espresso/ViewInteraction) class to improve the fluency of view actions and view matches in UI tests.
* [ViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/ViewMatchers.kt) – Provides methods for matching on a [View](https://developer.android.com/reference/android/view/View) in a UI test.
* [ViewPagerMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/ViewPagerMatchers.kt) – Provides methods for matching on a [ViewPager](https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager) in a UI test.

## SQLite

* [QueryPlanExplainer](library/src/main/java/com/tazkiyatech/utils/sqlite/QueryPlanExplainer.kt) – Provides methods for explaining the strategy or plan that SQLite will use to implement a specific SQL query.

## UiAutomator

* [UiDeviceExtensions](library/src/main/java/com/tazkiyatech/utils/uiautomator/UiDeviceExtensions.kt) – Provides extension functions on the [UiDevice](https://developer.android.com/reference/androidx/test/uiautomator/UiDevice) class for waiting on the device's "launcher" (a.k.a. "home") and "recent apps" screens.

## Setup

To use the above utilities within your app simply add the following repository and dependency declaration in the `build.gradle` file of your Android project:
 
    repositories {
        jcenter()
    }
    dependencies {
        androidTestImplementation 'com.tazkiyatech:android-test-utils:0.0.7'
    }
