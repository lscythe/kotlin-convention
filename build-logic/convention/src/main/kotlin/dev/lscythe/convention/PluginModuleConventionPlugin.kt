package dev.lscythe.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.samWithReceiver.gradle.SamWithReceiverExtension

/**
 * Convention plugin for Gradle plugin modules.
 *
 * Configures:
 * - java-gradle-plugin
 * - kotlin-jvm
 * - kotlin-sam-with-receiver
 * - JVM toolchain
 * - Testing with JUnit Platform
 */
class PluginModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("java-gradle-plugin")
                apply("org.jetbrains.kotlin.jvm")
                apply("org.jetbrains.kotlin.plugin.sam.with.receiver")
            }

            extensions.configure<SamWithReceiverExtension> {
                annotation("org.gradle.api.HasImplicitReceiver")
            }

            extensions.configure<KotlinJvmProjectExtension> {
                jvmToolchain(21)
            }

            extensions.configure<GradlePluginDevelopmentExtension> {
                website.set("https://github.com/lscythe/kotlin-convention")
                vcsUrl.set("https://github.com/lscythe/kotlin-convention")
            }

            dependencies {
                add("implementation", gradleKotlinDsl())
                add("testImplementation", project(":testing"))
            }

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}
