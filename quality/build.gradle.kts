plugins {
    id("convention.plugin-module")
    id("convention.publishing")
}

dependencies {
    implementation(project(":config"))
    api(utils.gradle.detekt)
    api(utils.gradle.spotless)
    api(libs.ktlint.gradle)
}

gradlePlugin {
    plugins {
        register("quality") {
            id = "dev.lscythe.convention.quality"
            displayName = "Quality Plugin"
            description = "Code quality tools: ktlint, detekt, spotless"
            implementationClass = "dev.lscythe.convention.quality.QualityPlugin"
            tags.set(listOf("kotlin", "quality", "ktlint", "detekt", "spotless"))
        }
    }
}
