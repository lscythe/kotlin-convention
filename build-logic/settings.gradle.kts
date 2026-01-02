pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
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
        create("libs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"

include(":convention")
