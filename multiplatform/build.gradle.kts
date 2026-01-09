plugins {
    id("convention.plugin-module")
    id("convention.publishing")
    alias(libs.plugins.kotlin.samwithreceiver)
    alias(libs.plugins.binaryCompatibilityValidator)
}

dependencies {
    implementation(project(":config"))
    implementation(project(":versioning"))
    implementation(project(":quality"))
    
    implementation(kotlinx.gradle.kotlin)
    implementation(compose.gradle.compose.compiler)
    implementation(compose.gradle.compose)
}

gradlePlugin {
    plugins {
        register("multiplatformLibrary") {
            id = "dev.lscythe.convention.library.multiplatform"
            displayName = "Multiplatform Library Plugin"
            description = "Convention plugin for Kotlin Multiplatform libraries"
            implementationClass = "dev.lscythe.convention.multiplatform.MultiplatformLibraryPlugin"
            tags.set(listOf("kotlin", "multiplatform", "library", "convention"))
        }
        register("multiplatformComposeLibrary") {
            id = "dev.lscythe.convention.library.multiplatform.compose"
            displayName = "Multiplatform Compose Library Plugin"
            description = "Convention plugin for Compose Multiplatform libraries"
            implementationClass = "dev.lscythe.convention.multiplatform.MultiplatformComposeLibraryPlugin"
            tags.set(listOf("kotlin", "multiplatform", "compose", "library", "convention"))
        }
    }
}

