# JVM Module

Convention plugin for Kotlin JVM libraries.

## Plugin ID

```
dev.lscythe.convention.library.kotlin
```

## Features

- Applies kotlin-jvm plugin
- Configures JVM toolchain from commonConfig (default: JDK 21)
- Applies quality plugin (ktlint, detekt, spotless)
- Applies versioning plugin
- Configures tests with JUnit Platform

## Usage

```kotlin
plugins {
    id("dev.lscythe.convention.library.kotlin")
}
```

## Tasks

| Task | Description |
|------|-------------|
| `projectTest` | Runs tests for all modules (root project) |
| `projectLint` | Runs lint checks for all modules (root project) |
| `test` | Run unit tests |
| `lintKotlin` | Check code style |
| `detekt` | Static analysis |

## With CommonConfig

If the root project uses the config plugin, JVM toolchain is configured from `commonConfig.javaVersion`:

```kotlin
// root build.gradle.kts
plugins {
    id("dev.lscythe.convention.config")
}

commonConfig {
    javaVersion.set(JavaVersion.VERSION_21)
}
```

## License

MIT License
