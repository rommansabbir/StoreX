package com.rommansabbir.storex.v2.search

import com.rommansabbir.storex.FileUtils
import java.io.File

/**
 * Search for file saved with `.storexfile` extension.
 *
 * @param filePath Path to be searched for files with `.storexfile` extension.
 *
 * @return [MutableList]<[File]>
 */
internal fun searchStoreAbleObject(filePath: String): MutableList<File?> {
    val fileExtension = ".storexfile"
    val files: MutableList<File?> = mutableListOf()
    FileUtils.getAllNestedFiles(
        filePath,
        files,
        fileExtension
    )
    return files
}