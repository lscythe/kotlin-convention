package dev.lscythe.convention.multiplatform

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * Convention plugin for Compose Multiplatform libraries.
 * Applies org.jetbrains.kotlin.multiplatform, org.jetbrains.kotlin.plugin.compose, and org.jetbrains.compose.
 */
class MultiplatformComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.multiplatform")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "org.jetbrains.compose")
        }
    }
}
