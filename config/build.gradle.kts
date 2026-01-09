plugins {
    id("convention.plugin-module")
    id("convention.publishing")
    alias(libs.plugins.kotlin.samwithreceiver)
    alias(libs.plugins.binaryCompatibilityValidator)
}

gradlePlugin {
    plugins {
        register("commonSettings") {
            id = "dev.lscythe.convention.config"
            displayName = "Common Settings Plugin"
            description = "Common configuration for Kotlin projects"
            implementationClass = "dev.lscythe.convention.config.CommonSettingsPlugin"
            tags.set(listOf("kotlin", "convention", "configuration"))
        }
    }
}
