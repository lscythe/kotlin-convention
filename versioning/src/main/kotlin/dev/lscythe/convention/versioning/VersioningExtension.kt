package dev.lscythe.convention.versioning

import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

/**
 * Extension for versioning configuration.
 */
abstract class VersioningExtension {
    /**
     * The version name derived from git tags.
     */
    abstract val versionName: Property<String>

    /**
     * The version code derived from version name.
     * Format: MAJOR * 10000 + MINOR * 100 + PATCH
     */
    abstract val versionCode: Property<Int>

    /**
     * Whether this is a snapshot version.
     */
    abstract val snapshot: Property<Boolean>

    /**
     * The git tag that this version is based on.
     */
    abstract val gitTag: Property<String>
}
