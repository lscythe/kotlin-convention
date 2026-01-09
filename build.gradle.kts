plugins {
    alias(kotlinx.plugins.jvm) apply false
    alias(kotlinx.plugins.multiplatform) apply false
    alias(androidx.plugins.library) apply false
    alias(androidx.plugins.application) apply false
    alias(utils.plugins.detekt) apply false
    alias(utils.plugins.spotless) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.kotlin.samwithreceiver) apply false
    alias(libs.plugins.binaryCompatibilityValidator) apply false
}

group = property("GROUP").toString()
version = property("VERSION").toString()

val projectCodeStyle by tasks.registering {
    group = "verification"
    description = "Runs all code style checks"
    dependsOn(provider { subprojects.mapNotNull { it.tasks.findByName("lintKotlin") } })
    dependsOn(provider { subprojects.mapNotNull { it.tasks.findByName("detekt") } })
}

val projectCodeStyleFix by tasks.registering {
    group = "formatting"
    description = "Fixes code style issues"
    dependsOn(provider { subprojects.mapNotNull { it.tasks.findByName("formatKotlin") } })
    dependsOn(provider { subprojects.mapNotNull { it.tasks.findByName("spotlessApply") } })
}

val projectCoverage by tasks.registering {
    group = "verification"
    description = "Generates code coverage report for all modules"
    dependsOn(provider { subprojects.mapNotNull { it.tasks.findByName("koverHtmlReport") } })
}