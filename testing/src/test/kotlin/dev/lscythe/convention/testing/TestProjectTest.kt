package dev.lscythe.convention.testing

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class TestProjectTest : FunSpec({

    test("create should create a project directory") {
        val project = TestProject.create("test-project-creation")
        project shouldNotBe null
    }

    test("buildFile should create build.gradle.kts") {
        val project = TestProject.create("test-build-file")
        project.buildFile("""
            plugins {
                id("base")
            }
        """)
        project.settingsFile("")
        
        val result = project.build("tasks")
        result.output.contains("BUILD SUCCESSFUL") shouldBe true
    }

    test("sourceFile should create file at specified path") {
        val project = TestProject.create("test-source-file")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("base")
            }
        """)
        project.sourceFile("src/test.txt", "test content")
        
        val result = project.build("tasks")
        result.output.contains("BUILD SUCCESSFUL") shouldBe true
    }
})
