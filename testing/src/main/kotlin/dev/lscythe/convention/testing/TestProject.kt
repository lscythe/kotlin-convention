package dev.lscythe.convention.testing

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File

/**
 * Test utility for creating temporary Gradle projects for plugin testing.
 */
class TestProject(
    private val projectDir: File,
) {
    init {
        projectDir.mkdirs()
    }

    /**
     * Creates a build.gradle.kts file with the given content.
     */
    fun buildFile(content: String): TestProject {
        File(projectDir, "build.gradle.kts").writeText(content.trimIndent())
        return this
    }

    /**
     * Creates a settings.gradle.kts file with the given content.
     */
    fun settingsFile(content: String): TestProject {
        File(projectDir, "settings.gradle.kts").writeText(content.trimIndent())
        return this
    }

    /**
     * Creates a gradle.properties file with the given content.
     */
    fun propertiesFile(content: String): TestProject {
        File(projectDir, "gradle.properties").writeText(content.trimIndent())
        return this
    }

    /**
     * Creates a source file at the specified path.
     */
    fun sourceFile(path: String, content: String): TestProject {
        val file = File(projectDir, path)
        file.parentFile.mkdirs()
        file.writeText(content.trimIndent())
        return this
    }

    /**
     * Creates a Kotlin source file in src/main/kotlin.
     */
    fun kotlinFile(packagePath: String, fileName: String, content: String): TestProject {
        val path = "src/main/kotlin/${packagePath.replace('.', '/')}/$fileName"
        return sourceFile(path, content)
    }

    /**
     * Runs a Gradle build with the specified tasks.
     */
    fun build(vararg tasks: String): BuildResult {
        return gradleRunner(*tasks).build()
    }

    /**
     * Runs a Gradle build expecting failure.
     */
    fun buildAndFail(vararg tasks: String): BuildResult {
        return gradleRunner(*tasks).buildAndFail()
    }

    private fun gradleRunner(vararg tasks: String): GradleRunner {
        return GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments(*tasks, "--stacktrace")
            .withPluginClasspath()
            .forwardOutput()
    }

    companion object {
        /**
         * Creates a new test project in a temporary directory.
         */
        fun create(name: String = "test-project"): TestProject {
            val tempDir = createTempDir(prefix = name)
            return TestProject(tempDir)
        }
    }
}

/**
 * Creates a temporary directory for testing.
 */
private fun createTempDir(prefix: String): File {
    val tempDir = File(System.getProperty("java.io.tmpdir"), "$prefix-${System.currentTimeMillis()}")
    tempDir.mkdirs()
    return tempDir
}
