package app.revanced.patcher

import app.revanced.patcher.patch.ResourcePatchContext
import brut.androlib.Config
import java.io.File
import java.util.logging.Logger
import java.util.zip.ZipFile

/**
 * The configuration for the patcher.
 *
 * @param apkFile The apk file to patch.
 * @param temporaryFilesPath A path to a folder to store temporary files in.
 * @param aaptBinaryPath A path to a custom aapt binary.
 * @param frameworkFileDirectory A path to the directory to cache the framework file in.
 */
class XapkPatcherConfig(
    apkFile: File,
    temporaryFilesPath: File = File("revanced-temporary-files"),
    aaptBinaryPath: String? = null,
    frameworkFileDirectory: String? = null,
) : BaseConfig(apkFile, temporaryFilesPath, aaptBinaryPath, frameworkFileDirectory) {
    internal val isXapk = true
    internal val xapkTemporaryFiles = temporaryFilesPath.resolve("xapk")
    public val xapkBaseApk = xapkTemporaryFiles.resolve("com.spotify.music.apk") //TODO

    internal fun unzipXapk() {
        logger.info("Unzipping Xapk")

        xapkTemporaryFiles.mkdirs()

        ZipFile(apkFile).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                val outFile = File(xapkTemporaryFiles, entry.name)
                zip.getInputStream(entry).use { input ->
                    outFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }
}
