# Android Test Utils Library

This library contains a mixture of small helper classes useful for testing Android projects.

## SQLite

* [QueryPlanExplainer](library/src/main/java/com/tazkiyatech/utils/sqlite/QueryPlanExplainer.java) – Provides helper methods for explaining the strategy or plan that SQLite will use to implement a specific SQL query.

## Setup

To use the above utilities within your app simply add the following repository and dependency declaration in the `build.gradle` file of your Android project:
 
    repositories {
        jcenter()
    }
    dependencies {
        androidTestImplementation 'com.tazkiyatech:android-test-utils:0.0.3'
    }
