package dev.lscythe.convention.quality

import org.gradle.api.provider.Property

/**
 * Extension for configuring code quality tools.
 */
abstract class QualityExtension {
    /**
     * Enable ktlint for code formatting. Default: true
     */
    abstract val ktlintEnabled: Property<Boolean>

    /**
     * Enable detekt for static analysis. Default: true
     */
    abstract val detektEnabled: Property<Boolean>

    /**
     * Enable spotless for code formatting. Default: true
     */
    abstract val spotlessEnabled: Property<Boolean>

    /**
     * Fail build on code style violations. Default: true
     */
    abstract val failOnViolation: Property<Boolean>

    init {
        ktlintEnabled.convention(true)
        detektEnabled.convention(true)
        spotlessEnabled.convention(true)
        failOnViolation.convention(true)
    }
}
