# Versioning Module

Tag-based semantic versioning plugin for Gradle projects.

## Plugin ID

```
dev.lscythe.convention.versioning
```

## Usage

```kotlin
plugins {
    id("dev.lscythe.convention.versioning")
}

// Access version info
println(versioning.versionName.get())  // e.g., "1.2.3" or "1.2.3-SNAPSHOT"
println(versioning.versionCode.get())  // e.g., 10203
println(versioning.isSnapshot.get())   // true if uncommitted changes exist
```

## Version Calculation

- Reads version from the latest git tag
- Appends `-SNAPSHOT` if there are commits since the tag or uncommitted changes
- Version code: `MAJOR * 10000 + MINOR * 100 + PATCH`

## Extension Properties

| Property | Type | Description |
|----------|------|-------------|
| versionName | String | Version string (e.g., "1.2.3-SNAPSHOT") |
| versionCode | Int | Numeric version code |
| isSnapshot | Boolean | True if this is a snapshot build |
| gitTag | String | The git tag used for versioning |

## License

MIT License
