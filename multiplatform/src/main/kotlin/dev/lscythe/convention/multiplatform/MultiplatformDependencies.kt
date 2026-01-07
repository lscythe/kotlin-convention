package dev.lscythe.convention.multiplatform

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configure common dependencies for Multiplatform libraries.
 * Adds standard dependencies that most KMP libraries need:
 * - Coroutines
 * - Kotlinx Serialization
 * - DateTime
 * - Logging (log4k)
 * - Testing (turbine)
 */
internal fun Project.configureCommonDependencies() {
    val versionCatalogs = extensions.getByType<VersionCatalogsExtension>()
    val kotlinx = versionCatalogs.find("kotlinx").orElse(null)
    val utils = versionCatalogs.find("utils").orElse(null)

    configure<KotlinMultiplatformExtension> {
        sourceSets.apply {
            commonMain.dependencies {
                kotlinx?.findLibrary("coroutines-core")?.ifPresent {
                    implementation(it)
                }

                kotlinx?.findLibrary("serialization-json")?.ifPresent {
                    implementation(it)
                }

                kotlinx?.findLibrary("datetime")?.ifPresent {
                    implementation(it)
                }

                kotlinx?.findLibrary("collections-immutable")?.ifPresent {
                    implementation(it)
                }

                utils?.findLibrary("log4k")?.ifPresent {
                    implementation(it)
                }
            }
            
            commonTest.dependencies {
                implementation(kotlin("test"))

                kotlinx?.findLibrary("coroutines-test")?.ifPresent {
                    implementation(it)
                }

                utils?.findLibrary("turbine")?.ifPresent {
                    implementation(it)
                }
            }
        }
    }
}
