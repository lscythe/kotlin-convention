package dev.lscythe.convention.config

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class CommonSettingsPluginTest : FunSpec({

    test("plugin should apply to root project") {
        val project = TestProject.create("config-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.config")
            }
            
            commonConfig {
                javaVersion.set(org.gradle.api.JavaVersion.VERSION_21)
                androidCompileSdk.set(35)
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("extension should have default values") {
        val project = TestProject.create("config-defaults-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.config")
            }
            
            tasks.register("printConfig") {
                doLast {
                    println("javaVersion=" + commonConfig.javaVersion.get())
                    println("androidCompileSdk=" + commonConfig.androidCompileSdk.get())
                    println("androidMinSdk=" + commonConfig.androidMinSdk.get())
                }
            }
        """)

        val result = project.build("printConfig")
        result.output shouldContain "javaVersion=17"
        result.output shouldContain "androidCompileSdk=35"
        result.output shouldContain "androidMinSdk=24"
    }

    test("plugin should fail when applied to subproject") {
        val project = TestProject.create("config-subproject-test")
        project.settingsFile("""
            include("subproject")
        """)
        project.buildFile("")
        project.sourceFile("subproject/build.gradle.kts", """
            plugins {
                id("dev.lscythe.convention.config")
            }
        """)

        val result = project.buildAndFail(":subproject:tasks")
        result.output shouldContain "should only be applied to the root project"
    }
})
