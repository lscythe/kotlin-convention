package dev.lscythe.convention.config

import org.gradle.api.JavaVersion
import org.gradle.api.provider.Property

/**
 * Extension for configuring common settings across all modules.
 */
abstract class CommonConfigExtension {
    /**
     * Java version for compilation. Default: 17
     */
    abstract val javaVersion: Property<JavaVersion>

    /**
     * Android compile SDK version. Default: 35
     */
    abstract val androidCompileSdk: Property<Int>

    /**
     * Android min SDK version. Default: 24
     */
    abstract val androidMinSdk: Property<Int>

    /**
     * Android target SDK version. Default: 35
     */
    abstract val androidTargetSdk: Property<Int>

    init {
        javaVersion.convention(JavaVersion.VERSION_17)
        androidCompileSdk.convention(35)
        androidMinSdk.convention(24)
        androidTargetSdk.convention(35)
    }
}
