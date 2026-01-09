package dev.lscythe.convention.android

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin for Android libraries.
 * Applies Android library plugin with sensible defaults.
 */
class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("dev.lscythe.convention.quality")
            apply("dev.lscythe.convention.versioning")
        }

        configureAndroid(target)
        target.configureKotlinAndroid()
    }

    private fun configureAndroid(project: Project) {
        project.extensions.configure<LibraryExtension> {
            project.configureAndroidCommon(this)

            defaultConfig {
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            buildFeatures {
                buildConfig = false
            }
        }
    }
}
