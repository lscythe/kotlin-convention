# Android Module

Convention plugins for Android libraries and applications, with optional Compose support.

## Plugin IDs

| Plugin ID | Description |
|-----------|-------------|
| `dev.lscythe.convention.library.android` | Android library |
| `dev.lscythe.convention.library.android.compose` | Android Compose library |
| `dev.lscythe.convention.application.android` | Android application |
| `dev.lscythe.convention.application.android.compose` | Android Compose application |

## Features

- Applies Android Gradle Plugin
- Configures SDK versions from commonConfig (default: compileSdk 36, minSdk 24, targetSdk 36)
- Applies kotlin-android plugin
- Applies quality and versioning plugins
- Disables BuildConfig for libraries
- Recognizes `src/main/kotlin` source sets
- Sets version code/name from versioning plugin (applications)

### Compose Plugins Additional Features

- Applies kotlin-compose-compiler plugin
- Enables Compose build features
- Configures Compose compiler metrics/reports (via gradle properties)
- Adds common Compose dependencies from version catalog (BOM, ui-tooling)

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

### Compose Library

```kotlin
plugins {
    id("dev.lscythe.convention.library.android.compose")
}

android {
    namespace = "com.example.compose.library"
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

### Compose Application

```kotlin
plugins {
    id("dev.lscythe.convention.application.android.compose")
}

android {
    namespace = "com.example.compose.app"
}
```

## Compose Compiler Configuration

Enable metrics and reports via gradle properties:

```properties
# gradle.properties
enableComposeCompilerMetrics=true
enableComposeCompilerReports=true
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
    androidCompileSdk.set(36)
    androidMinSdk.set(24)
    androidTargetSdk.set(36)
}
```

## License

MIT License
