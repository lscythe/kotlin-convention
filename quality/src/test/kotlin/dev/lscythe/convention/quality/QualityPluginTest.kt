package dev.lscythe.convention.quality

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class QualityPluginTest : FunSpec({

    test("plugin should apply successfully") {
        val project = TestProject.create("quality-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.3.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("plugin should register lintKotlin task") {
        val project = TestProject.create("quality-lint-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.3.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "lintKotlin"
    }

    test("plugin should register detekt task") {
        val project = TestProject.create("quality-detekt-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.3.0"
                id("dev.lscythe.convention.quality")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "detekt"
    }

    test("quality tools can be disabled") {
        val project = TestProject.create("quality-disabled-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.3.0"
                id("dev.lscythe.convention.quality")
            }
            
            quality {
                ktlintEnabled.set(false)
                detektEnabled.set(false)
                spotlessEnabled.set(false)
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }
})
