package dev.lscythe.convention.quality

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class QualityPluginTest : FunSpec({

    fun settingsWithPluginRepos() = """
        pluginManagement {
            repositories {
                gradlePluginPortal()
                mavenCentral()
                google()
            }
        }
    """

    test("plugin should apply successfully") {
        val project = TestProject.create("quality-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.1.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("plugin should register lintKotlin task") {
        val project = TestProject.create("quality-lint-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.1.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "lintKotlin"
    }

    test("plugin should register detekt task") {
        val project = TestProject.create("quality-detekt-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.1.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "detekt"
    }

    test("plugin should register kover task") {
        val project = TestProject.create("quality-kover-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.1.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "koverHtmlReport"
    }

    test("quality tools can be disabled") {
        val project = TestProject.create("quality-disabled-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.1.0"
                id("dev.lscythe.convention.quality")
            }
            
            quality {
                ktlintEnabled.set(false)
                detektEnabled.set(false)
                spotlessEnabled.set(false)
                koverEnabled.set(false)
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }
})
