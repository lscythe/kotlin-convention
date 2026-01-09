plugins {
    id("convention.plugin-module")
    id("convention.publishing")
    alias(libs.plugins.kotlin.samwithreceiver)
    alias(libs.plugins.binaryCompatibilityValidator)
}

dependencies {
    implementation(project(":config"))
    api(utils.gradle.detekt)
    api(utils.gradle.spotless)
    api(utils.gradle.kover)
    api(libs.ktlint.gradle)
    
    implementation(kotlinx.gradle.kotlin)
}

gradlePlugin {
    plugins {
        register("quality") {
            id = "dev.lscythe.convention.quality"
            displayName = "Quality Plugin"
            description = "Code quality tools: ktlint, detekt, spotless, kover"
            implementationClass = "dev.lscythe.convention.quality.QualityPlugin"
            tags.set(listOf("kotlin", "quality", "ktlint", "detekt", "spotless", "kover", "coverage"))
        }
    }
}
