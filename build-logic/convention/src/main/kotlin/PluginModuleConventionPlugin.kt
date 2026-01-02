import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Convention plugin for building Gradle plugin modules.
 * Configures Kotlin, testing with kotest, and common settings.
 */
class PluginModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("java-gradle-plugin")
            apply("org.jetbrains.kotlin.jvm")
            apply("org.jetbrains.kotlin.plugin.sam.with.receiver")
        }

        extensions.configure(KotlinJvmProjectExtension::class.java) {
            jvmToolchain(21)
        }

        extensions.configure(org.gradle.plugin.devel.GradlePluginDevelopmentExtension::class.java) {
            website.set("https://github.com/lscythe/convention")
            vcsUrl.set("https://github.com/lscythe/convention")
        }

        dependencies {
            "testImplementation"(project(":testing"))
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}
