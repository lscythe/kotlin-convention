plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(kotlinx.gradle.kotlin)
    implementation(kotlinx.gradle.binarycompatibility)
    implementation(utils.gradle.detekt)
    implementation(utils.gradle.spotless)
    implementation(utils.gradle.maven.publish)
    implementation(libs.ktlint.gradle)
    
    // Android Gradle Plugin
    compileOnly(androidx.gradle.android)
}

kotlin {
    jvmToolchain(libs.versions.java.compilation.get().toInt())
}
