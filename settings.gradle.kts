@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("kotlinx") {
            from("dev.lscythe.tool:versions-kotlinx:2025.12.23")
        }
        create("androidx") {
            from("dev.lscythe.tool:versions-androidx:2025.12.23")
        }
        create("utils") {
            from("dev.lscythe.tool:versions-utils:2025.12.23")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "convention"

include(
    ":config",
    ":versioning",
    ":quality",
    ":jvm",
    ":android",
    ":multiplatform",
    ":testing",
)