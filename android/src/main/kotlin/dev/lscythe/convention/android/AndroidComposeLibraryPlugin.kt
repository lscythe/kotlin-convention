package dev.lscythe.convention.android

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

/**
 * Convention plugin for adding Compose support to Android libraries.
 * 
 * This is an additive plugin - apply it alongside the base library convention:
 * ```
 * plugins {
 *     id("dev.lscythe.convention.library.android")
 *     id("dev.lscythe.convention.library.android.compose")
 * }
 * ```
 */
class AndroidComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
