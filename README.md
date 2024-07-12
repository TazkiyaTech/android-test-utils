# Android Test Utils

This library contains classes and methods that will ease the writing of your Android instrumentation tests.

## Espresso

|                                   |                                                                                                                                      |
|-----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| [MenuDropDownListViewMatchers][1] | Provides methods for matching on a `MenuPopupWindow.MenuDropDownListView` in a UI test.                                              |
| [RecyclerViewMatchers][2]         | Provides methods for matching on a [RecyclerView][13] in a UI test.                                                                  |
| [TabViewMatchers][3]              | Provides methods for matching on a [TabLayout.TabView][14] in a UI test.                                                             |
| [TextViewMatchers][4]             | Provides methods for matching on a [TextView][15] in a UI test.                                                                      |
| [ViewActions][5]                  | Provides a method for waiting on a [View][16] to match a certain condition in a UI test.                                             |
| [ViewInteractionExtensions][6]    | Provides extension functions on the [ViewInteraction][17] class to improve the fluency of view actions and view matches in UI tests. |
| [ViewMatchers][7]                 | Provides methods for matching on a [View][16] in a UI test.                                                                          |
| [ViewPagerMatchers][8]            | Provides methods for matching on a [ViewPager][18] in a UI test.                                                                     |
| [ViewPager2Actions][9]            | Provides methods for interacting with a [ViewPager2][19] in a UI test.                                                               |
| [ViewPager2Matchers][10]          | Provides methods for matching on a [ViewPager2][19] in a UI test.                                                                    |

## SQLite

|                          |                                                                                                              |
|--------------------------|--------------------------------------------------------------------------------------------------------------|
| [QueryPlanExplainer][11] | Provides methods for explaining the strategy or plan that SQLite will use to implement a specific SQL query. |

## UiAutomator

|                          |                                                                                                                                            |
|--------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| [UiDeviceExtensions][12] | Provides extension functions on the [UiDevice][20] class for waiting on the device's "launcher" (a.k.a. "home") and "recent apps" screens. |

## Setup

To use the above utilities within your app simply add the following repository and dependency declaration in the `build.gradle` file of your Android project:

```groovy
repositories {
    mavenCentral()
}
dependencies {
    androidTestImplementation("com.tazkiyatech:android-test-utils:3.0.0")
}
```

[1]: library/src/main/java/com/tazkiyatech/utils/espresso/MenuDropDownListViewMatchers.kt
[2]: library/src/main/java/com/tazkiyatech/utils/espresso/RecyclerViewMatchers.kt
[3]: library/src/main/java/com/tazkiyatech/utils/espresso/TabViewMatchers.kt
[4]: library/src/main/java/com/tazkiyatech/utils/espresso/TextViewMatchers.kt
[5]: library/src/main/java/com/tazkiyatech/utils/espresso/ViewActions.kt
[6]: library/src/main/java/com/tazkiyatech/utils/espresso/ViewInteractionExtensions.kt
[7]: library/src/main/java/com/tazkiyatech/utils/espresso/ViewMatchers.kt
[8]: library/src/main/java/com/tazkiyatech/utils/espresso/ViewPagerMatchers.kt
[9]: library/src/main/java/com/tazkiyatech/utils/espresso/ViewPager2Actions.kt
[10]: library/src/main/java/com/tazkiyatech/utils/espresso/ViewPager2Matchers.kt
[11]: library/src/main/java/com/tazkiyatech/utils/sqlite/QueryPlanExplainer.kt
[12]: library/src/main/java/com/tazkiyatech/utils/uiautomator/UiDeviceExtensions.kt
[13]: https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView
[14]: https://developer.android.com/reference/com/google/android/material/tabs/TabLayout.TabView
[15]: https://developer.android.com/reference/android/widget/TextView
[16]: https://developer.android.com/reference/android/view/View
[17]: https://developer.android.com/reference/androidx/test/espresso/ViewInteraction
[18]: https://developer.android.com/reference/androidx/viewpager/widget/ViewPager
[19]: https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
[20]: https://developer.android.com/reference/androidx/test/uiautomator/UiDevice
