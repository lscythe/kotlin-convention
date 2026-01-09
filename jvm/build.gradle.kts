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
}

gradlePlugin {
    plugins {
        register("jvmLibrary") {
            id = "dev.lscythe.convention.library.jvm"
            displayName = "JVM Library Plugin"
            description = "Convention plugin for JVM libraries using Kotlin"
            implementationClass = "dev.lscythe.convention.jvm.JvmLibraryPlugin"
            tags.set(listOf("kotlin", "jvm", "library", "convention"))
        }
    }
}
