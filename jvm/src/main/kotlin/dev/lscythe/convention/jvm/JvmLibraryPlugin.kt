package dev.lscythe.convention.jvm

import dev.lscythe.convention.config.CommonConfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Convention plugin for JVM libraries using Kotlin.
 * Applies kotlin-jvm, quality, and versioning plugins with sensible defaults.
 */
class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
            apply("dev.lscythe.convention.quality")
            apply("dev.lscythe.convention.versioning")
        }

        configureKotlin(target)
        configureTesting(target)
        registerAggregateTasks(target)
    }

    private fun configureKotlin(project: Project) {
        val commonConfig = project.rootProject.extensions.findByType<CommonConfigExtension>()
        val javaVersion = commonConfig?.javaVersion?.get()?.majorVersion?.toInt() ?: 21

        project.extensions.configure(KotlinJvmProjectExtension::class.java) {
            jvmToolchain(javaVersion)
        }
    }

    private fun configureTesting(project: Project) {
        project.tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }

    private fun registerAggregateTasks(project: Project) {
        if (project == project.rootProject) {
            project.tasks.register("projectTest") {
                group = "verification"
                description = "Runs tests for all modules"
                dependsOn(project.subprojects.mapNotNull { it.tasks.findByName("test") })
            }

            project.tasks.register("projectLint") {
                group = "verification"
                description = "Runs lint checks for all modules"
                dependsOn(project.subprojects.mapNotNull { it.tasks.findByName("lintKotlin") })
            }
        }
    }
}
