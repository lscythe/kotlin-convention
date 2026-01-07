plugins {
    alias(kotlinx.plugins.jvm) apply false
    alias(kotlinx.plugins.multiplatform) apply false
    alias(androidx.plugins.library) apply false
    alias(androidx.plugins.application) apply false
    alias(utils.plugins.detekt) apply false
    alias(utils.plugins.spotless) apply false
   alias(libs.plugins.ktlint) apply false
}

group = property("GROUP").toString()
version = property("VERSION").toString()