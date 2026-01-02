# Testing Module

Shared test utilities for Gradle plugin testing using Gradle TestKit.

## Usage

Add as a test dependency:

```kotlin
dependencies {
    testImplementation(project(":testing"))
}
```

## TestProject

Helper class for creating temporary Gradle projects:

```kotlin
class MyPluginTest : FunSpec({
    test("plugin should apply successfully") {
        val project = TestProject.create("my-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("my.plugin")
            }
        """)
        
        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }
})
```

## Included Dependencies

- kotest-runner-junit5
- kotest-assertions-core
- kotest-property
- mockk
- Gradle TestKit
