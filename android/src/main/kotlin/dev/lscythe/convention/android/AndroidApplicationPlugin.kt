package dev.lscythe.convention.android

import com.android.build.api.dsl.ApplicationExtension
import dev.lscythe.convention.config.CommonConfigExtension
import dev.lscythe.convention.versioning.VersioningExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Convention plugin for Android applications.
 * Applies Android application plugin with sensible defaults.
 */
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("dev.lscythe.convention.quality")
            apply("dev.lscythe.convention.versioning")
        }

        configureAndroid(target)
        configureKotlin(target)
    }

    private fun configureAndroid(project: Project) {
        val commonConfig = project.rootProject.extensions.findByType<CommonConfigExtension>()
        val versioning = project.extensions.findByType<VersioningExtension>()

        project.extensions.configure<ApplicationExtension> {
            compileSdk = commonConfig?.androidCompileSdk?.get() ?: 35

            defaultConfig {
                minSdk = commonConfig?.androidMinSdk?.get() ?: 24
                targetSdk = commonConfig?.androidTargetSdk?.get() ?: 35
                
                versionCode = versioning?.versionCode?.get() ?: 1
                versionName = versioning?.versionName?.get() ?: "0.0.1"
                
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
                debug {
                    isDebuggable = true
                }
            }

            compileOptions {
                val javaVersion = commonConfig?.javaVersion?.get() ?: JavaVersion.VERSION_17
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }

            sourceSets {
                getByName("main") {
                    kotlin.srcDir("src/main/kotlin")
                }
                getByName("test") {
                    kotlin.srcDir("src/test/kotlin")
                }
                getByName("androidTest") {
                    kotlin.srcDir("src/androidTest/kotlin")
                }
            }
        }
    }

    private fun configureKotlin(project: Project) {
        val commonConfig = project.rootProject.extensions.findByType<CommonConfigExtension>()
        val javaVersion = commonConfig?.javaVersion?.get()?.majorVersion?.toInt() ?: 17

        project.extensions.configure<KotlinAndroidProjectExtension> {
            jvmToolchain(javaVersion)
        }
    }
}
