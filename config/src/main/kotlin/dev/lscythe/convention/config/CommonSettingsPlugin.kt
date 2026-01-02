package dev.lscythe.convention.config

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin that provides common configuration for the root project.
 * Creates a 'commonConfig' extension for sharing settings across modules.
 */
class CommonSettingsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        require(target == target.rootProject) {
            "CommonSettingsPlugin should only be applied to the root project"
        }

        val extension = target.extensions.create(
            EXTENSION_NAME,
            CommonConfigExtension::class.java
        )

        target.subprojects {
            extensions.add(EXTENSION_NAME, extension)
        }
    }

    companion object {
        const val EXTENSION_NAME = "commonConfig"
    }
}
