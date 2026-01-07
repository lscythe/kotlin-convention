package dev.lscythe.convention

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import java.net.URI

/**
 * Convention plugin for Maven publishing.
 *
 * Configures:
 * - Maven Central publishing with automatic release
 * - Publication signing
 * - POM metadata (licenses, developers, SCM)
 * - GitHub Packages repository
 */
class PublishingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.vanniktech.maven.publish")
                apply("maven-publish")
            }

            val version = providers.environmentVariable("VERSION")
                .orElse(providers.gradleProperty("VERSION"))
                .orElse("0.0.0-SNAPSHOT")

            extensions.configure<MavenPublishBaseExtension> {
                publishToMavenCentral(automaticRelease = true, validateDeployment = true)
                signAllPublications()
                coordinates(
                    groupId = providers.gradleProperty("GROUP").get(),
                    artifactId = project.name,
                    version = version.get()
                )

                pom {
                    name.set(providers.gradleProperty("POM_NAME").orElse(project.name).get())
                    description.set(
                        providers.gradleProperty("POM_DESCRIPTION")
                            .orElse(project.description ?: "Kotlin Convention Plugin").get()
                    )
                    url.set(
                        providers.gradleProperty("POM_URL")
                            .orElse("https://github.com/lscythe/kotlin-convention").get()
                    )
                    inceptionYear.set("2025")

                    licenses {
                        license {
                            name.set(
                                providers.gradleProperty("POM_LICENCE_NAME")
                                    .orElse("MIT License").get()
                            )
                            url.set(
                                providers.gradleProperty("POM_LICENCE_URL")
                                    .orElse("https://opensource.org/licenses/MIT").get()
                            )
                        }
                    }

                    developers {
                        developer {
                            id.set(providers.gradleProperty("POM_DEVELOPER_ID").orElse("lscythe").get())
                            name.set(providers.gradleProperty("POM_DEVELOPER_NAME").orElse("Lscythe").get())
                            url.set("https://github.com/lscythe")
                        }
                    }

                    scm {
                        url.set(
                            providers.gradleProperty("POM_SCM_URL")
                                .orElse("https://github.com/lscythe/kotlin-convention").get()
                        )
                        connection.set(
                            providers.gradleProperty("POM_SCM_CONNECTION")
                                .orElse("scm:git:git://github.com/lscythe/kotlin-convention.git").get()
                        )
                        developerConnection.set(
                            providers.gradleProperty("POM_SCM_DEV_CONNECTION")
                                .orElse("scm:git:ssh://git@github.com/lscythe/kotlin-convention.git").get()
                        )
                    }
                }
            }

            extensions.configure<PublishingExtension> {
                repositories {
                    maven {
                        name = "GitHubPackages"
                        url = URI("https://maven.pkg.github.com/lscythe/kotlin-convention")
                        credentials {
                            username = providers.environmentVariable("GITHUB_ACTOR").orNull
                            password = providers.environmentVariable("GITHUB_TOKEN").orNull
                        }
                    }
                }
            }
        }
    }
}
