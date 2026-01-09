package dev.lscythe.convention.versioning

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class VersioningPluginTest : FunSpec({

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
        val project = TestProject.create("versioning-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.versioning")
            }
            
            tasks.register("printVersion") {
                doLast {
                    println("versionName=" + versioning.versionName.get())
                    println("versionCode=" + versioning.versionCode.get())
                    println("snapshot=" + versioning.snapshot.get())
                }
            }
        """)

        val result = project.build("printVersion")
        result.output shouldContain "BUILD SUCCESSFUL"
        result.output shouldContain "versionName="
    }

    test("default version when no git tags") {
        val project = TestProject.create("versioning-default-test")
        project.settingsFile(settingsWithPluginRepos())
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.versioning")
            }
            
            tasks.register("printVersion") {
                doLast {
                    println("snapshot=" + versioning.snapshot.get())
                }
            }
        """)

        val result = project.build("printVersion")
        result.output shouldContain "snapshot=true"
    }
})
