package dev.lscythe.convention.multiplatform

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class MultiplatformLibraryPluginTest : FunSpec({

    test("plugin should apply successfully") {
        val project = TestProject.create("multiplatform-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.multiplatform")
            }
            
            kotlin {
                jvm()
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("plugin should configure JVM target") {
        val project = TestProject.create("multiplatform-jvm-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.multiplatform")
            }
            
            kotlin {
                jvm()
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "compileKotlinJvm"
    }

    test("plugin should apply quality plugin") {
        val project = TestProject.create("multiplatform-quality-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.multiplatform")
            }
            
            kotlin {
                jvm()
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "lintKotlin"
    }

    test("plugin should apply versioning plugin") {
        val project = TestProject.create("multiplatform-versioning-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.multiplatform")
            }
            
            kotlin {
                jvm()
            }
            
            tasks.register("printVersion") {
                doLast {
                    println("version=" + versioning.versionName.get())
                }
            }
        """)

        val result = project.build("printVersion")
        result.output shouldContain "version="
    }
})
