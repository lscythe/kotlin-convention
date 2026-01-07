package dev.lscythe.convention.quality

import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/**
 * Plugin that configures code quality tools: ktlint, detekt, and spotless.
 */
class QualityPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create(
            EXTENSION_NAME,
            QualityExtension::class.java
        )

        target.afterEvaluate {
            if (extension.ktlintEnabled.get()) {
                configureKtlint(target)
            }
            if (extension.detektEnabled.get()) {
                configureDetekt(target)
            }
            if (extension.spotlessEnabled.get()) {
                configureSpotless(target)
            }
            registerAggregateTasks(target)
        }
    }

    private fun configureKtlint(project: Project) {
        project.pluginManager.apply("org.jlleitschuh.gradle.ktlint")

        project.extensions.configure<KtlintExtension> {
            ignoreFailures.set(false)
        }
    }

    private fun configureDetekt(project: Project) {
        project.pluginManager.apply("io.gitlab.arturbosch.detekt")

        project.extensions.configure<DetektExtension> {
            buildUponDefaultConfig = true
            allRules = false
            config.setFrom(project.rootProject.files("config/detekt/detekt.yml"))
            baseline = project.file("detekt-baseline.xml")
        }
    }

    private fun configureSpotless(project: Project) {
        project.pluginManager.apply("com.diffplug.spotless")

        project.extensions.configure<SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**")
                trimTrailingWhitespace()
                endWithNewline()
            }
            kotlinGradle {
                target("**/*.gradle.kts")
                targetExclude("**/build/**")
                trimTrailingWhitespace()
                endWithNewline()
            }
        }
    }

    private fun registerAggregateTasks(project: Project) {
        if (project == project.rootProject) {
            project.tasks.register("projectCodeStyle") {
                group = "verification"
                description = "Runs all code style checks"
                dependsOn(project.subprojects.mapNotNull { it.tasks.findByName("lintKotlin") })
                dependsOn(project.subprojects.mapNotNull { it.tasks.findByName("detekt") })
            }

            project.tasks.register("projectCodeStyleFix") {
                group = "formatting"
                description = "Fixes code style issues"
                dependsOn(project.subprojects.mapNotNull { it.tasks.findByName("formatKotlin") })
                dependsOn(project.subprojects.mapNotNull { it.tasks.findByName("spotlessApply") })
            }
        }
    }

    companion object {
        const val EXTENSION_NAME = "quality"
    }
}
