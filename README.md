# Android Test Utils

This library contains a mixture of small helper classes useful for testing Android projects.

## Espresso

* [RecyclerViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/RecyclerViewMatchers.kt) – Provides methods to match on certain properties of a [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView).
* [TextViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/TextViewMatchers.kt) – Provides methods to match on certain properties of a [TextView](https://developer.android.com/reference/android/widget/TextView).
* [ViewPagerMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/ViewPagerMatchers.kt) – Provides methods to match on certain properties of a [ViewPager](https://developer.android.com/reference/kotlin/androidx/viewpager/widget/ViewPager).

## SQLite

* [QueryPlanExplainer](library/src/main/java/com/tazkiyatech/utils/sqlite/QueryPlanExplainer.java) – Provides methods for explaining the strategy or plan that SQLite will use to implement a specific SQL query.

## UiAutomator

* [UiDeviceExtensions](library/src/main/java/com/tazkiyatech/utils/uiautomator/UiDeviceExtensions.kt) – Provides extension functions on the [UiDevice](https://developer.android.com/reference/androidx/test/uiautomator/UiDevice) class for waiting on the device's launcher screen and its recent apps screen.

## Setup

To use the above utilities within your app simply add the following repository and dependency declaration in the `build.gradle` file of your Android project:
 
    repositories {
        jcenter()
    }
    dependencies {
        androidTestImplementation 'com.tazkiyatech:android-test-utils:0.0.7'
    }
