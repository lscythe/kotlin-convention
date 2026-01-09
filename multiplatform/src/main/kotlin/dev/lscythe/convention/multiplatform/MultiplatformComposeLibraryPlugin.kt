package dev.lscythe.convention.multiplatform

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * Convention plugin for adding Compose support to Kotlin Multiplatform libraries.
 * 
 * This is an additive plugin - apply it alongside the base multiplatform library convention:
 * ```
 * plugins {
 *     id("dev.lscythe.convention.library.multiplatform")
 *     id("dev.lscythe.convention.library.multiplatform.compose")
 * }
 * ```
 */
class MultiplatformComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "org.jetbrains.compose")
        }
    }
}
