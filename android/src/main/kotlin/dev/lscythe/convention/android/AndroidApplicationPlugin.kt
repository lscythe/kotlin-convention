package dev.lscythe.convention.android

import com.android.build.api.dsl.ApplicationExtension
import dev.lscythe.convention.config.CommonConfigExtension
import dev.lscythe.convention.versioning.VersioningExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType

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
        target.configureKotlinAndroid()
    }

    private fun configureAndroid(project: Project) {
        val commonConfig = project.rootProject.extensions.findByType<CommonConfigExtension>()
        val versioning = project.extensions.findByType<VersioningExtension>()

        project.extensions.configure<ApplicationExtension> {
            project.configureAndroidCommon(this)

            defaultConfig {
                targetSdk = commonConfig?.androidTargetSdk?.get() ?: 36
                
                versionCode = versioning?.versionCode?.get() ?: 1
                versionName = versioning?.versionName?.get() ?: "0.0.1"
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
        }
    }
}
