plugins {
    id("convention.plugin-module")
    id("convention.publishing")
}

dependencies {
    implementation(project(":config"))
    implementation(project(":versioning"))
    implementation(project(":quality"))
    implementation(project(":jvm"))
    
    compileOnly(androidx.gradle.android)
    compileOnly(kotlinx.gradle.kotlin)
    compileOnly(compose.gradle.compose.compiler)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "dev.lscythe.convention.library.android"
            displayName = "Android Library Plugin"
            description = "Convention plugin for Android libraries"
            implementationClass = "dev.lscythe.convention.android.AndroidLibraryPlugin"
            tags.set(listOf("android", "library", "convention"))
        }
        register("androidApplication") {
            id = "dev.lscythe.convention.application.android"
            displayName = "Android Application Plugin"
            description = "Convention plugin for Android applications"
            implementationClass = "dev.lscythe.convention.android.AndroidApplicationPlugin"
            tags.set(listOf("android", "application", "convention"))
        }
        register("androidComposeLibrary") {
            id = "dev.lscythe.convention.library.android.compose"
            displayName = "Android Compose Library Plugin"
            description = "Convention plugin for Android Compose libraries"
            implementationClass = "dev.lscythe.convention.android.AndroidComposeLibraryPlugin"
            tags.set(listOf("android", "library", "compose", "convention"))
        }
        register("androidComposeApplication") {
            id = "dev.lscythe.convention.application.android.compose"
            displayName = "Android Compose Application Plugin"
            description = "Convention plugin for Android Compose applications"
            implementationClass = "dev.lscythe.convention.android.AndroidComposeApplicationPlugin"
            tags.set(listOf("android", "application", "compose", "convention"))
        }
    }
}

