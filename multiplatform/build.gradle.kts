plugins {
    id("convention.plugin-module")
    id("convention.publishing")
}

dependencies {
    implementation(project(":config"))
    implementation(project(":versioning"))
    implementation(project(":quality"))
    
    compileOnly(kotlinx.gradle.kotlin)
    
    // MocKMP for multiplatform mocking in tests
    testImplementation(libs.mockmp.runtime)
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
    }
}
