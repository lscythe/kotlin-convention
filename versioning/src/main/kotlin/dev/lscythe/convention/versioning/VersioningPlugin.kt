package dev.lscythe.convention.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logging
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

/**
 * Plugin that provides tag-based semantic versioning.
 * Reads version from git tags in the format: MAJOR.MINOR.PATCH
 */
abstract class VersioningPlugin @Inject constructor(
    private val execOperations: ExecOperations
) : Plugin<Project> {
    private val logger = Logging.getLogger(VersioningPlugin::class.java)

    override fun apply(target: Project) {
        val extension = target.extensions.create(
            EXTENSION_NAME,
            VersioningExtension::class.java
        )

        val versionInfo = getVersionFromGit(target)

        extension.gitTag.set(versionInfo.tag)
        extension.versionName.set(versionInfo.versionName)
        extension.versionCode.set(versionInfo.versionCode)
        extension.snapshot.set(versionInfo.isSnapshot)

        // Set project version
        target.version = versionInfo.versionName
    }

    private fun getVersionFromGit(project: Project): VersionInfo {
        val tag = runGitCommand(project, "describe", "--tags", "--abbrev=0")
            ?: return VersionInfo.DEFAULT

        val isClean = runGitCommand(project, "status", "--porcelain")?.isBlank() ?: false
        val commitsSinceTag = runGitCommand(project, "rev-list", "$tag..HEAD", "--count")
            ?.trim()?.toIntOrNull() ?: 0

        val isSnapshot = commitsSinceTag > 0 || !isClean
        val versionName = if (isSnapshot) "$tag-SNAPSHOT" else tag
        val versionCode = parseVersionCode(tag)

        return VersionInfo(
            tag = tag,
            versionName = versionName,
            versionCode = versionCode,
            isSnapshot = isSnapshot
        )
    }

    private fun runGitCommand(project: Project, vararg args: String): String? {
        return try {
            val output = ByteArrayOutputStream()
            val result = execOperations.exec {
                workingDir = project.rootDir
                commandLine = listOf("git") + args.toList()
                standardOutput = output
                isIgnoreExitValue = true
            }
            if (result.exitValue == 0) {
                output.toString().trim()
            } else {
                null
            }
        } catch (e: Exception) {
            logger.debug("Git command failed: ${args.joinToString(" ")}", e)
            null
        }
    }

    private fun parseVersionCode(tag: String): Int {
        val cleanTag = tag.removePrefix("v")
        val parts = cleanTag.split(".").mapNotNull { it.toIntOrNull() }
        
        return when (parts.size) {
            3 -> parts[0] * 10000 + parts[1] * 100 + parts[2]
            2 -> parts[0] * 10000 + parts[1] * 100
            1 -> parts[0] * 10000
            else -> 1
        }
    }

    data class VersionInfo(
        val tag: String,
        val versionName: String,
        val versionCode: Int,
        val isSnapshot: Boolean,
    ) {
        companion object {
            val DEFAULT = VersionInfo(
                tag = "0.0.1",
                versionName = "0.0.1-SNAPSHOT",
                versionCode = 1,
                isSnapshot = true
            )
        }
    }

    companion object {
        const val EXTENSION_NAME = "versioning"
    }
}
