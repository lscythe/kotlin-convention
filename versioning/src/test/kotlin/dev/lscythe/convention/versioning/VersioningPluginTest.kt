package dev.lscythe.convention.versioning

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class VersioningPluginTest : FunSpec({

    test("plugin should apply successfully") {
        val project = TestProject.create("versioning-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.versioning")
            }
            
            tasks.register("printVersion") {
                doLast {
                    println("versionName=" + versioning.versionName.get())
                    println("versionCode=" + versioning.versionCode.get())
                    println("isSnapshot=" + versioning.isSnapshot.get())
                }
            }
        """)

        val result = project.build("printVersion")
        result.output shouldContain "BUILD SUCCESSFUL"
        result.output shouldContain "versionName="
    }

    test("default version when no git tags") {
        val project = TestProject.create("versioning-default-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.versioning")
            }
            
            tasks.register("printVersion") {
                doLast {
                    println("isSnapshot=" + versioning.isSnapshot.get())
                }
            }
        """)

        val result = project.build("printVersion")
        result.output shouldContain "isSnapshot=true"
    }
})
