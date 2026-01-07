# Multiplatform Module

Convention plugins for Kotlin Multiplatform libraries, with optional Compose Multiplatform support.

## Plugin IDs

| Plugin ID | Description |
|-----------|-------------|
| `dev.lscythe.convention.library.multiplatform` | Kotlin Multiplatform library |
| `dev.lscythe.convention.library.multiplatform.compose` | Compose Multiplatform library |

## Features

### Standard Library Plugin

- Applies kotlin-multiplatform plugin
- Applies kotlin-serialization plugin
- Configures JVM toolchain from commonConfig (default: JDK 21)
- Applies quality plugin (ktlint, detekt, spotless)
- Applies versioning plugin
- Configures JVM target with JUnit Platform for tests
- **Adds common dependencies automatically:**
  - Coroutines
  - Kotlinx Serialization
  - Kotlinx DateTime
  - Kotlinx Immutable Collections
  - Log4k (multiplatform logging)
  - Turbine (Flow testing)

### Compose Multiplatform Plugin

- All features from standard library plugin
- Applies kotlin-compose-compiler plugin
- Applies org.jetbrains.compose plugin

## Usage

### Standard Library

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

### Compose Multiplatform Library

```kotlin
plugins {
    id("dev.lscythe.convention.library.multiplatform.compose")
}

kotlin {
    jvm()
    // Add other targets
}
```

## Common Dependencies Included

The multiplatform library plugin automatically adds these dependencies:

**commonMain:**
- `kotlinx-coroutines-core`
- `kotlinx-serialization-json`
- `kotlinx-datetime`
- `kotlinx-collections-immutable`
- `log4k`

**commonTest:**
- `kotlin-test`
- `kotlinx-coroutines-test`
- `turbine`

## Testing with Kotest

For Kotest multiplatform testing, apply the kotest-multiplatform plugin in your project:

```kotlin
plugins {
    id("dev.lscythe.convention.library.multiplatform")
    id("io.kotest.multiplatform")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(utils.bundles.kotest.multiplatform)
        }
        jvmTest.dependencies {
            implementation(utils.kotest.runner)
        }
    }
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

## License

MIT License
