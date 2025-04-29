package app.revanced.patcher

import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.patch.Patch
import app.revanced.patcher.patch.ResourcePatchContext
import brut.androlib.apk.ApkInfo
import brut.directory.ExtFile
import java.io.Closeable

/**
 * A context for the patcher containing the current state of the patcher.
 *
 * @param config The configuration for the patcher.
 */
@Suppress("MemberVisibilityCanBePrivate")
class XapkPatcherContext internal constructor(config: XapkPatcherConfig): Closeable {
    /**
     * [PackageMetadata] of the supplied [BaseConfig.apkFile].
     */
    val packageMetadata = PackageMetadata(ApkInfo(ExtFile(config.baseApkFile)))

    val archApkFile = config.splitsApkFiles.find { it.name.contains("arm64") } //TODO

    /**
     * The set of [Patch]es.
     */
    internal val executablePatches = mutableSetOf<Patch<*>>()

    /**
     * The set of all [Patch]es and their dependencies.
     */
    internal val allPatches = mutableSetOf<Patch<*>>()

    /**
     * The context for patches containing the current state of the resources.
     */
    init {
        config.apkFile = archApkFile!!
    }
    internal val resourceContext = ResourcePatchContext(packageMetadata, config)

    /**
     * The context for patches containing the current state of the bytecode.
     */
    init {
        config.apkFile = config.baseApkFile
    }
    internal val bytecodeContext = BytecodePatchContext(config)

    init {
        config.apkFile = config.baseApkFile
    }

    override fun close() = bytecodeContext.close()
}
