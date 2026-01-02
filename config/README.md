# Config Module

Provides common configuration for Kotlin projects via the `commonConfig` extension.

## Plugin ID

```
dev.lscythe.convention.config
```

## Usage

Apply to the root project:

```kotlin
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

## Extension Properties

| Property | Default | Description |
|----------|---------|-------------|
| javaVersion | VERSION_17 | Java version for compilation |
| androidCompileSdk | 35 | Android compile SDK |
| androidMinSdk | 24 | Android minimum SDK |
| androidTargetSdk | 35 | Android target SDK |

The extension is automatically available in all subprojects.
