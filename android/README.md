# Android Module

Convention plugins for Android libraries and applications.

## Plugin IDs

```
dev.lscythe.convention.library.android
dev.lscythe.convention.application.android
```

## Features

- Applies Android Gradle Plugin
- Configures SDK versions from commonConfig
- Applies kotlin-android plugin
- Applies quality and versioning plugins
- Disables BuildConfig for libraries
- Recognizes `src/main/kotlin` source sets
- Sets version code/name from versioning plugin (applications)

## Usage

### Library

```kotlin
plugins {
    id("dev.lscythe.convention.library.android")
}

android {
    namespace = "com.example.library"
}
```

### Application

```kotlin
plugins {
    id("dev.lscythe.convention.application.android")
}

android {
    namespace = "com.example.app"
}
```

## With CommonConfig

SDK versions and Java version are read from `commonConfig` if available:

```kotlin
// root build.gradle.kts
plugins {
    id("dev.lscythe.convention.config")
}

commonConfig {
    javaVersion.set(JavaVersion.VERSION_21)
    androidCompileSdk.set(35)
    androidMinSdk.set(24)
    androidTargetSdk.set(35)
}
```
