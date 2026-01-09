plugins {
    alias(kotlinx.plugins.jvm)
    alias(libs.plugins.kotlin.samwithreceiver)
    alias(libs.plugins.binaryCompatibilityValidator)
}

dependencies {
    api(gradleTestKit())
    api(utils.bundles.kotest)
    api(utils.mockk)
}

tasks.test {
    useJUnitPlatform()
}
