pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("junit5", "5.10.1")
            library("junit-jupiter-api", "org.junit.jupiter", "junit-jupiter-api").versionRef("junit5")
            library("junit-jupiter-engine", "org.junit.jupiter", "junit-jupiter-engine").versionRef("junit5")
            library("junit-jupiter-params", "org.junit.jupiter", "junit-jupiter-params").versionRef("junit5")
        }
    }
}

include(":library")
