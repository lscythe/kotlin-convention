plugins {
    alias(kotlinx.plugins.jvm) apply false
    alias(kotlinx.plugins.multiplatform) apply false
    alias(androidx.plugins.android.library) apply false
    alias(androidx.plugins.android.application) apply false
    alias(utils.plugins.detekt) apply false
    alias(utils.plugins.spotless) apply false
    alias(libs.plugins.ktlint) apply false
}

group = property("GROUP").toString()
version = property("VERSION").toString()