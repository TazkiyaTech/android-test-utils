import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    `maven-publish`
    signing
}

configure<LibraryExtension> {
    compileSdk = 36
    namespace = "com.tazkiyatech.utils"

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    lint {
        abortOnError = true
    }

    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.test.core)

    api(libs.compose.ui.test)
    api(libs.espresso.contrib)
    api(libs.uiautomator)

    testRuntimeOnly(libs.junit.platform.launcher)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)

    androidTestImplementation(libs.test.ext.junit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }

            groupId = properties["groupId"].toString()
            artifactId = properties["artifactId"].toString()
            version = properties["version"].toString()

            pom {
                name = project.properties["artifactId"].toString()
                description =
                    "An Android library containing classes and methods designed to ease the writing of Android instrumentation tests."
                url = "https://github.com/TazkiyaTech/android-test-utils"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                organization {
                    name = "Tazkiya Tech"
                    url = "http://tazkiyatech.com"
                }
                developers {
                    developer {
                        id = "adil-hussain-84"
                        name = "Adil Hussain"
                        email = "adilson05uk@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/TazkiyaTech/android-test-utils.git"
                    developerConnection = "scm:git:ssh://github.com:TazkiyaTech/android-test-utils.git"
                    url = "https://github.com/TazkiyaTech/android-test-utils"
                }
            }
        }
    }

    repositories {
        maven {
            name = "sonatype"
            credentials(PasswordCredentials::class)
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
}

signing {
    // the "signing.keyId", "signing.password" and "signing.secretKeyRingFile" properties required by this task are defined outside of this project in the "~/.gradle/gradle.properties" file
    sign(publishing.publications["release"])
}
