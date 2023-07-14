# Android Test Utils

This library contains classes and methods that will ease the writing of your Android instrumentation tests.

## Espresso

* [MenuDropDownListViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/MenuDropDownListViewMatchers.kt) – Provides methods for matching on a `MenuPopupWindow.MenuDropDownListView` in a UI test.
* [RecyclerViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/RecyclerViewMatchers.kt) – Provides methods for matching on a [RecyclerView](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView) in a UI test.
* [TabViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/TabViewMatchers.kt) – Provides methods for matching on a [TabLayout.TabView](https://developer.android.com/reference/com/google/android/material/tabs/TabLayout.TabView) in a UI test.
* [TextViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/TextViewMatchers.kt) – Provides methods for matching on a [TextView](https://developer.android.com/reference/android/widget/TextView) in a UI test.
* [ViewActions](library/src/main/java/com/tazkiyatech/utils/espresso/ViewActions.kt) – Provides a method for waiting on a [View](https://developer.android.com/reference/android/view/View) to match a certain condition in a UI test.
* [ViewInteractionExtensions](library/src/main/java/com/tazkiyatech/utils/espresso/ViewInteractionExtensions.kt) – Provides extension functions on the [ViewInteraction](https://developer.android.com/reference/androidx/test/espresso/ViewInteraction) class to improve the fluency of view actions and view matches in UI tests.
* [ViewMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/ViewMatchers.kt) – Provides methods for matching on a [View](https://developer.android.com/reference/android/view/View) in a UI test.
* [ViewPagerMatchers](library/src/main/java/com/tazkiyatech/utils/espresso/ViewPagerMatchers.kt) – Provides methods for matching on a [ViewPager](https://developer.android.com/reference/androidx/viewpager/widget/ViewPager) in a UI test.
* [ViewPager2Actions](library/src/main/java/com/tazkiyatech/utils/espresso/ViewPager2Actions.java) – Provides methods for interacting with a [ViewPager2](https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2) in a UI test.
* [ViewPager2Matchers](library/src/main/java/com/tazkiyatech/utils/espresso/ViewPager2Matchers.kt) – Provides methods for matching on a [ViewPager2](https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2) in a UI test.

## SQLite

* [QueryPlanExplainer](library/src/main/java/com/tazkiyatech/utils/sqlite/QueryPlanExplainer.kt) – Provides methods for explaining the strategy or plan that SQLite will use to implement a specific SQL query.

## UiAutomator

* [UiDeviceExtensions](library/src/main/java/com/tazkiyatech/utils/uiautomator/UiDeviceExtensions.kt) – Provides extension functions on the [UiDevice](https://developer.android.com/reference/androidx/test/uiautomator/UiDevice) class for waiting on the device's "launcher" (a.k.a. "home") and "recent apps" screens.

## Setup

To use the above utilities within your app simply add the following repository and dependency declaration in the `build.gradle` file of your Android project:

```groovy
repositories {
    mavenCentral()
}
dependencies {
    androidTestImplementation 'com.tazkiyatech:android-test-utils:1.9.0'
}
```
