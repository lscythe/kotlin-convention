package dev.lscythe.convention.multiplatform

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class MultiplatformLibraryPluginTest : FunSpec({

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
        val project = TestProject.create("multiplatform-test")
        project.settingsFile(settingsWithPluginRepos())
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
        project.settingsFile(settingsWithPluginRepos())
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
        project.settingsFile(settingsWithPluginRepos())
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
        project.settingsFile(settingsWithPluginRepos())
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

    test("compose plugin should apply successfully") {
        val project = TestProject.create("multiplatform-compose-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.multiplatform")
                id("dev.lscythe.convention.library.multiplatform.compose")
            }
            
            kotlin {
                jvm()
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("compose plugin should apply compose multiplatform") {
        val project = TestProject.create("multiplatform-compose-mp-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.multiplatform")
                id("dev.lscythe.convention.library.multiplatform.compose")
            }
            
            kotlin {
                jvm()
            }
            
            tasks.register("checkPlugins") {
                doLast {
                    println("compose-compiler=" + plugins.hasPlugin("org.jetbrains.kotlin.plugin.compose"))
                    println("compose-multiplatform=" + plugins.hasPlugin("org.jetbrains.compose"))
                }
            }
        """)

        val result = project.build("checkPlugins")
        result.output shouldContain "compose-compiler=true"
        result.output shouldContain "compose-multiplatform=true"
    }
})
