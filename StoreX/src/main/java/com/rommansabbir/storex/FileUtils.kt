package com.rommansabbir.storex

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

object FileUtils {
    /** The number of bytes in a kilobyte.  */
    val ONE_KB: Long = 1024

    /** The number of bytes in a megabyte.  */
    val ONE_MB = ONE_KB * ONE_KB

    /** The file copy buffer size (30 MB)  */
    private val FILE_COPY_BUFFER_SIZE = ONE_MB * 30


    /**
     * Get all files path from an given directory (including sub-directory).
     *
     * @param directoryName given directory name.
     * @param files where all the files will be stored.
     */
    fun getAllNestedFiles(
        directoryName: String,
        files: MutableList<File?>,
        extension: String? = null
    ) {
        val directoryItems = File(directoryName).listFiles()
        if (directoryItems != null) for (item: File in directoryItems) {
            if (item.isFile) {
                if (extension?.isNotEmpty() == true) {
                    val temp: String = item.path.substring(item.path.lastIndexOf("."))
                    if (temp.equals(extension, true)) {
                        files.add(item)
                    }
                } else {
                    files.add(item)
                }
            } else if (item.isDirectory) {
                getAllNestedFiles(item.absolutePath, files, extension)
            }
        }
    }
    // -----------------------------------------------------------------------
    /**
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws IOException in case deletion is unsuccessful
     */
    @Throws(IOException::class)
    fun deleteDirectory(directory: File) {
        if (!directory.exists()) {
            return
        }
        if (!isSymlink(directory)) {
            cleanDirectory(directory)
        }
        if (!directory.delete()) {
            val message = "Unable to delete directory $directory."
            throw IOException(message)
        }
    }

    /**
     * Deletes a file, never throwing an exception. If file is a directory, delete it and all
     * sub-directories.
     *
     *
     * The difference between File.delete() and this method are:
     *
     *
     *  * A directory to be deleted does not have to be empty.
     *  * No exceptions are thrown when a file or directory cannot be deleted.
     *
     *
     * @param file file or directory to delete, can be `null`
     * @return `true` if the file or directory was deleted, otherwise `false`
     * @since 1.4
     */
    fun deleteQuietly(file: File?): Boolean {
        if (file == null) {
            return false
        }
        try {
            if (file.isDirectory) {
                cleanDirectory(file)
            }
        } catch (ignored: Exception) {
        }
        return try {
            file.delete()
        } catch (ignored: Exception) {
            false
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     */
    @Throws(IOException::class)
    fun cleanDirectory(directory: File) {
        if (!directory.exists()) {
            val message = "$directory does not exist"
            throw IllegalArgumentException(message)
        }
        if (!directory.isDirectory) {
            val message = "$directory is not a directory"
            throw IllegalArgumentException(message)
        }
        val files = directory.listFiles()
            ?: // null if security restricted
            throw IOException("Failed to list contents of $directory")
        var exception: IOException? = null
        for (file: File in files) {
            try {
                forceDelete(file)
            } catch (ioe: IOException) {
                exception = ioe
            }
        }
        if (null != exception) {
            throw exception
        }
    }
    // -----------------------------------------------------------------------
    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     *
     *
     * The difference between File.delete() and this method are:
     *
     *
     *  * A directory to be deleted does not have to be empty.
     *  * You get exceptions when a file or directory cannot be deleted. (java.io.File methods
     * returns a boolean)
     *
     *
     * @param file file or directory to delete, must not be `null`
     * @throws NullPointerException if the directory is `null`
     * @throws FileNotFoundException if the file was not found
     * @throws IOException in case deletion is unsuccessful
     */
    @Throws(IOException::class)
    fun forceDelete(file: File) {
        if (file.isDirectory) {
            deleteDirectory(file)
        } else {
            val filePresent = file.exists()
            if (!file.delete()) {
                if (!filePresent) {
                    throw FileNotFoundException("File does not exist: $file")
                }
                val message = "Unable to delete file: $file"
                throw IOException(message)
            }
        }
    }

    /**
     * Determines whether the specified file is a Symbolic Link rather than an actual file.
     *
     *
     * Will not return true if there is a Symbolic Link anywhere in the path, only if the
     * specific file is.
     *
     *
     * For code that runs on Java 1.7 or later, use the following method instead: <br></br>
     * `boolean java.nio.file.Files.isSymbolicLink(Path path)`
     *
     * @param file the file to check
     * @return true if the file is a Symbolic Link
     * @throws IOException if an IO error occurs while checking the file
     * @since 2.0
     */
    @Throws(IOException::class)
    fun isSymlink(file: File?): Boolean {
        if (file == null) {
            throw NullPointerException("File must not be null")
        }
        //    if (FilenameUtils.isSystemWindows()) {
        //      return false;
        //    }
        val fileInCanonicalDir: File =
            if (file.parent == null) {
                file
            } else {
                val canonicalDir = file.parentFile?.canonicalFile
                File(canonicalDir, file.name)
            }
        return fileInCanonicalDir.canonicalFile != fileInCanonicalDir.absoluteFile
    }

}
