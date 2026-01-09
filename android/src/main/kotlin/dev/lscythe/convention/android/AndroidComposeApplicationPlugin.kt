package dev.lscythe.convention.android

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

/**
 * Convention plugin for adding Compose support to Android applications.
 * 
 * This is an additive plugin - apply it alongside the base application convention:
 * ```
 * plugins {
 *     id("dev.lscythe.convention.application.android")
 *     id("dev.lscythe.convention.application.android.compose")
 * }
 * ```
 */
class AndroidComposeApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}
