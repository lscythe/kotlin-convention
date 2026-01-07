plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

dependencies {
    implementation(kotlinx.gradle.kotlin)
    implementation(kotlinx.gradle.binarycompatibility)
    implementation(utils.gradle.detekt)
    implementation(utils.gradle.spotless)
    implementation(libs.ktlint.gradle)

    implementation(utils.gradle.maven.publish)
    implementation(libs.kotlin.sam.with.receiver)

    compileOnly(androidx.gradle.android)
}

kotlin {
    jvmToolchain(libs.versions.java.compilation.get().toInt())
}

gradlePlugin {
    plugins {
        register("plugin-module") {
            id = "convention.plugin-module"
            displayName = "Plugin Module Convention"
            description = "Convention plugin for Gradle plugin modules"
            implementationClass = "dev.lscythe.convention.PluginModuleConventionPlugin"
        }
        register("publishing") {
            id = "convention.publishing"
            displayName = "Publishing Convention"
            description = "Convention plugin for Maven publishing"
            implementationClass = "dev.lscythe.convention.PublishingConventionPlugin"
        }
    }
}