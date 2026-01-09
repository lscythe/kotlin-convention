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
            from("dev.lscythe.tool:versions-kotlinx:2026.01.09")
        }
        create("androidx") {
            from("dev.lscythe.tool:versions-androidx:2026.01.09")
        }
        create("compose") {
            from("dev.lscythe.tool:versions-compose-multiplatform:2026.01.09")
        }
        create("utils") {
            from("dev.lscythe.tool:versions-utils:2026.01.09")
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