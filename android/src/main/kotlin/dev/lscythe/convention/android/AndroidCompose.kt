package dev.lscythe.convention.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Configure Compose-specific options for Android projects.
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            // Add common Compose dependencies from the project's libs catalog
            val versionCatalogs = extensions.findByType(VersionCatalogsExtension::class.java)
            val androidx = versionCatalogs?.find("androidx")?.orElse(null)
            
            if (androidx != null) {
                // Add Compose BOM if available
                androidx.findLibrary("compose-bom").ifPresent { bom ->
                    add("implementation", platform(bom))
                    add("androidTestImplementation", platform(bom))
                }
                
                // Add common compose dependencies if available
                androidx.findLibrary("compose-ui-tooling-preview").ifPresent {
                    add("implementation", it)
                }
                androidx.findLibrary("compose-ui-tooling").ifPresent {
                    add("debugImplementation", it)
                }
            }
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            project.isolated.rootProject.projectDirectory
                .dir("build")
                .dir(projectDir.toRelativeString(rootDir))
                .let { project.provider { it.dir(dir) } }
        }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles
            .addAll(
                project.isolated.rootProject.projectDirectory.file("compose_compiler_config.conf")
            )
    }
}
