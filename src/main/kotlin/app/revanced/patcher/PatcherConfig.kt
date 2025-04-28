package app.revanced.patcher

import app.revanced.patcher.patch.ResourcePatchContext
import brut.androlib.Config
import java.io.File
import java.util.logging.Logger

/**
 * The configuration for the patcher.
 *
 * @param apkFile The apk file to patch.
 * @param temporaryFilesPath A path to a folder to store temporary files in.
 * @param aaptBinaryPath A path to a custom aapt binary.
 * @param frameworkFileDirectory A path to the directory to cache the framework file in.
 */
open class PatcherConfig(
    apkFile: File,
    temporaryFilesPath: File = File("revanced-temporary-files"),
    aaptBinaryPath: String? = null,
    frameworkFileDirectory: String? = null,
    val xapkBaseApk: File? = null
) : BaseConfig(apkFile, temporaryFilesPath, aaptBinaryPath, frameworkFileDirectory) {

}