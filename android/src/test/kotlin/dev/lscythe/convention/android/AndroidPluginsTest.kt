package dev.lscythe.convention.android

import dev.lscythe.convention.testing.TestProject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class AndroidPluginsTest : FunSpec({

    test("AndroidLibraryPlugin should apply successfully") {
        val project = TestProject.create("android-lib-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.android")
            }
            
            android {
                namespace = "com.example.library"
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("AndroidLibraryPlugin should disable BuildConfig by default") {
        val project = TestProject.create("android-lib-buildconfig")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.android")
            }
            
            android {
                namespace = "com.example.library"
            }
            
            tasks.register("checkBuildConfig") {
                doLast {
                    println("buildConfig=" + android.buildFeatures.buildConfig)
                }
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("checkBuildConfig")
        result.output shouldContain "buildConfig=false"
    }

    test("AndroidApplicationPlugin should apply successfully") {
        val project = TestProject.create("android-app-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.application.android")
            }
            
            android {
                namespace = "com.example.app"
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("AndroidApplicationPlugin should set version from versioning plugin") {
        val project = TestProject.create("android-app-version")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.application.android")
            }
            
            android {
                namespace = "com.example.app"
            }
            
            tasks.register("printVersion") {
                doLast {
                    println("versionCode=" + android.defaultConfig.versionCode)
                    println("versionName=" + android.defaultConfig.versionName)
                }
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("printVersion")
        result.output shouldContain "versionCode="
        result.output shouldContain "versionName="
    }

    test("AndroidComposeLibraryPlugin should apply successfully") {
        val project = TestProject.create("android-compose-lib-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.android.compose")
            }
            
            android {
                namespace = "com.example.compose.library"
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("AndroidComposeLibraryPlugin should enable Compose") {
        val project = TestProject.create("android-compose-lib-enabled")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.library.android.compose")
            }
            
            android {
                namespace = "com.example.compose.library"
            }
            
            tasks.register("checkCompose") {
                doLast {
                    println("compose=" + android.buildFeatures.compose)
                }
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("checkCompose")
        result.output shouldContain "compose=true"
    }

    test("AndroidComposeApplicationPlugin should apply successfully") {
        val project = TestProject.create("android-compose-app-test")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.application.android.compose")
            }
            
            android {
                namespace = "com.example.compose.app"
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("tasks")
        result.output shouldContain "BUILD SUCCESSFUL"
    }

    test("AndroidComposeApplicationPlugin should enable Compose") {
        val project = TestProject.create("android-compose-app-enabled")
        project.settingsFile("")
        project.buildFile("""
            plugins {
                id("dev.lscythe.convention.application.android.compose")
            }
            
            android {
                namespace = "com.example.compose.app"
            }
            
            tasks.register("checkCompose") {
                doLast {
                    println("compose=" + android.buildFeatures.compose)
                }
            }
        """)
        project.propertiesFile("""
            android.useAndroidX=true
        """)

        val result = project.build("checkCompose")
        result.output shouldContain "compose=true"
    }
})

