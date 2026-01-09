package dev.lscythe.convention.android

import com.android.build.api.dsl.CommonExtension
import dev.lscythe.convention.config.CommonConfigExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Configure common Android settings for both libraries and applications.
 */
internal fun Project.configureAndroidCommon(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    val commonConfig = rootProject.extensions.findByType<CommonConfigExtension>()

    commonExtension.apply {
        compileSdk = commonConfig?.androidCompileSdk?.get() ?: 36

        defaultConfig {
            minSdk = commonConfig?.androidMinSdk?.get() ?: 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            val javaVersion = commonConfig?.javaVersion?.get() ?: JavaVersion.VERSION_21
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }
}

/**
 * Configure Kotlin for Android projects.
 */
internal fun Project.configureKotlinAndroid() {
    val commonConfig = rootProject.extensions.findByType<CommonConfigExtension>()
    val javaVersion = commonConfig?.javaVersion?.get()?.majorVersion?.toInt() ?: 21

    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(javaVersion)
    }
}
