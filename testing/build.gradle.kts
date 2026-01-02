plugins {
    alias(kotlinx.plugins.jvm)
}

dependencies {
    api(gradleTestKit())
    api(utils.bundles.kotest)
    api(utils.mockk)
}

tasks.test {
    useJUnitPlatform()
}
