buildscript {
    extra["junit5_version"] = "5.10.1"
}

plugins {
    id("com.android.library") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}

tasks.wrapper {
    gradleVersion = "8.5"
}
