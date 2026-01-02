import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

/**
 * Convention plugin for publishing Gradle plugins to Maven Central.
 */
class PublishingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.vanniktech.maven.publish")
        }

        extensions.configure<MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
            signAllPublications()

            coordinates(
                groupId = property("GROUP").toString(),
                artifactId = project.name,
                version = property("VERSION").toString()
            )

            pom {
                name.set(property("POM_NAME").toString())
                description.set(property("POM_DESCRIPTION").toString())
                url.set(property("POM_URL").toString())

                licenses {
                    license {
                        name.set(property("POM_LICENCE_NAME").toString())
                        url.set(property("POM_LICENCE_URL").toString())
                    }
                }

                developers {
                    developer {
                        id.set(property("POM_DEVELOPER_ID").toString())
                        name.set(property("POM_DEVELOPER_NAME").toString())
                    }
                }

                scm {
                    url.set(property("POM_SCM_URL").toString())
                    connection.set(property("POM_SCM_CONNECTION").toString())
                    developerConnection.set(property("POM_SCM_DEV_CONNECTION").toString())
                }
            }
        }
    }
}
