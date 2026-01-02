plugins {
    id("convention.plugin-module")
    id("convention.publishing")
}

dependencies {
    implementation(project(":config"))
    implementation(project(":versioning"))
    implementation(project(":quality"))
    
    compileOnly(kotlinx.gradle.kotlin)
}

gradlePlugin {
    plugins {
        register("kotlinLibrary") {
            id = "dev.lscythe.convention.library.kotlin"
            displayName = "Kotlin Library Plugin"
            description = "Convention plugin for Kotlin JVM libraries"
            implementationClass = "dev.lscythe.convention.jvm.KotlinLibraryPlugin"
            tags.set(listOf("kotlin", "library", "convention"))
        }
    }
}
