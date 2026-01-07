# Quality Module

Code quality tools plugin integrating ktlint, detekt, and spotless.

## Plugin ID

```
dev.lscythe.convention.quality
```

## Usage

```kotlin
plugins {
    id("dev.lscythe.convention.quality")
}

quality {
    ktlintEnabled.set(true)
    detektEnabled.set(true)
    spotlessEnabled.set(true)
    failOnViolation.set(true)
}
```

## Tasks

| Task | Description |
|------|-------------|
| `lintKotlin` | Check code style with ktlint |
| `formatKotlin` | Fix ktlint issues |
| `detekt` | Run detekt static analysis |
| `spotlessCheck` | Check formatting with spotless |
| `spotlessApply` | Fix formatting with spotless |
| `projectCodeStyle` | Run all checks (root project) |
| `projectCodeStyleFix` | Fix all issues (root project) |

## Configuration

### Detekt

Create `config/detekt/detekt.yml` in your project root for custom rules.

### Extension Properties

| Property | Default | Description |
|----------|---------|-------------|
| ktlintEnabled | true | Enable ktlint |
| detektEnabled | true | Enable detekt |
| spotlessEnabled | true | Enable spotless |
| failOnViolation | true | Fail build on violations |

## License

MIT License
