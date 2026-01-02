package dev.lscythe.convention.multiplatform

import dev.lscythe.convention.config.CommonConfigExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for Kotlin Multiplatform libraries.
 * Applies kotlin-multiplatform with quality and versioning plugins.
 */
class MultiplatformLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("dev.lscythe.convention.quality")
            apply("dev.lscythe.convention.versioning")
        }

        configureKotlin(target)
    }

    private fun configureKotlin(project: Project) {
        val commonConfig = project.rootProject.extensions.findByType<CommonConfigExtension>()
        val javaVersion = commonConfig?.javaVersion?.get()?.majorVersion?.toInt() ?: 17

        project.extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(javaVersion)

            // Configure JVM target by default
            jvm {
                compilations.all {
                    compileTaskProvider.configure {
                        compilerOptions {
                            freeCompilerArgs.add("-Xjdk-release=${javaVersion}")
                        }
                    }
                }
                testRuns.named("test") {
                    executionTask.configure {
                        useJUnitPlatform()
                    }
                }
            }

            sourceSets.apply {
                commonMain.dependencies {
                    // Common dependencies can be added here
                }
                commonTest.dependencies {
                    implementation(kotlin("test"))
                }
            }
        }
    }
}
