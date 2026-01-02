# Multiplatform Module

Convention plugin for Kotlin Multiplatform libraries.

## Plugin ID

```
dev.lscythe.convention.library.multiplatform
```

## Features

- Applies kotlin-multiplatform plugin
- Configures JVM toolchain from commonConfig
- Applies quality plugin (ktlint, detekt, spotless)
- Applies versioning plugin
- Configures JVM target with JUnit Platform for tests

## Usage

```kotlin
plugins {
    id("dev.lscythe.convention.library.multiplatform")
}

kotlin {
    jvm()
    // Add other targets as needed
    // iosArm64()
    // js()
}
```

## Testing

For multiplatform modules, use MocKMP instead of mockk:

```kotlin
dependencies {
    commonTestImplementation(libs.mockmp.runtime)
}
```

## With CommonConfig

JVM toolchain is configured from `commonConfig.javaVersion`:

```kotlin
// root build.gradle.kts
plugins {
    id("dev.lscythe.convention.config")
}

commonConfig {
    javaVersion.set(JavaVersion.VERSION_21)
}
```
