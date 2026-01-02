package dev.lscythe.convention.jvm

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class KotlinLibraryPluginTest : FunSpec({

    test("plugin should apply kotlin-jvm plugin") {
        val project = TestProject.create("jvm-kotlin-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.kotlin")
            }
        """)
        project.kotlinFile("com.example", "Main.kt", """
            package com.example
            
            fun main() {
                println("Hello")
            }
        """)

        val result = project.build("tasks")
        result.output shouldContain "compileKotlin"
    }

    test("plugin should apply quality plugin") {
        val project = TestProject.create("jvm-quality-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.kotlin")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "lintKotlin"
        result.output shouldContain "detekt"
    }

    test("plugin should apply versioning plugin") {
        val project = TestProject.create("jvm-versioning-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.kotlin")
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

    test("plugin should configure test task with JUnit Platform") {
        val project = TestProject.create("jvm-test-config")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.kotlin")
            }
        """)

        val result = project.build("tasks", "--all")
        result.output shouldContain "test"
    }
})
