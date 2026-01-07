# Convention Plugins
[![Build](https://github.com/lscythe/kotlin-convention/actions/workflows/ci.yml/badge.svg)](https://github.com/lscythe/kotlin-convention/actions/workflows/build.yml)
[![Security](https://github.com/lscythe/kotlin-convention/actions/workflows/security.yml/badge.svg)](https://github.com/lscythe/kotlin-convention/actions/workflows/security.yml)
[![Maven Central Version](https://img.shields.io/maven-central/v/dev.lscythe.tool/versions-androidx)](https://central.sonatype.com/search?q=g:dev.lscythe.tool)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-blue.svg)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Gradle convention plugins for Kotlin projects. Provides pre-configured plugins for JVM, Android, and Multiplatform projects with integrated quality tools and versioning.

## Installation

Add to your `settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
```

## Available Plugins

| Plugin ID | Description |
|-----------|-------------|
| `dev.lscythe.convention.config` | Common configuration extension |
| `dev.lscythe.convention.versioning` | Tag-based semantic versioning |
| `dev.lscythe.convention.quality` | Code quality (ktlint, detekt, spotless) |
| `dev.lscythe.convention.library.kotlin` | Kotlin JVM library |
| `dev.lscythe.convention.library.android` | Android library |
| `dev.lscythe.convention.application.android` | Android application |
| `dev.lscythe.convention.library.multiplatform` | Kotlin Multiplatform library |

## Quick Start

### Root Project

```kotlin
plugins {
    id("dev.lscythe.convention.config") version "<version>"
}

commonConfig {
    javaVersion.set(JavaVersion.VERSION_21)
    androidCompileSdk.set(35)
    androidMinSdk.set(24)
}
```

### Kotlin Library

```kotlin
plugins {
    id("dev.lscythe.convention.library.kotlin")
}
```

### Android Library

```kotlin
plugins {
    id("dev.lscythe.convention.library.android")
}

android {
    namespace = "com.example.library"
}
```

### Android Application

```kotlin
plugins {
    id("dev.lscythe.convention.application.android")
}

android {
    namespace = "com.example.app"
}
```

### Multiplatform Library

```kotlin
plugins {
    id("dev.lscythe.convention.library.multiplatform")
}

kotlin {
    jvm()
    // Add other targets
}
```

## Aggregate Tasks

| Task | Description |
|------|-------------|
| `projectTest` | Run tests for all modules |
| `projectLint` | Run lint checks for all modules |
| `projectCodeStyle` | Run all code style checks |
| `projectCodeStyleFix` | Fix code style issues |

## Module Documentation

- [config](config/README.md) - Common configuration
- [versioning](versioning/README.md) - Tag-based versioning
- [quality](quality/README.md) - Code quality tools
- [jvm](jvm/README.md) - Kotlin JVM library
- [android](android/README.md) - Android plugins
- [multiplatform](multiplatform/README.md) - Multiplatform library
- [testing](testing/README.md) - Test utilities

## License

Apache-2.0
