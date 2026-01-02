plugins {
    id("convention.plugin-module")
    id("convention.publishing")
}

dependencies {
    implementation(project(":config"))
}

gradlePlugin {
    plugins {
        register("versioning") {
            id = "dev.lscythe.convention.versioning"
            displayName = "Versioning Plugin"
            description = "Tag-based semantic versioning for Gradle projects"
            implementationClass = "dev.lscythe.convention.versioning.VersioningPlugin"
            tags.set(listOf("kotlin", "versioning", "git", "semver"))
        }
    }
}
