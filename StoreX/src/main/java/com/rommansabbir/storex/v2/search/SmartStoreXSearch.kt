package com.rommansabbir.storex.v2.search

import android.content.Context
import com.rommansabbir.storex.FileUtils
import java.io.File

internal class SmartStoreXSearch {
    fun getAllKeys(context: Context): MutableList<String> {
        val fileExtension = ".storexfile"
        val files: MutableList<File?> = mutableListOf()
        FileUtils.getAllNestedFiles(
            context.applicationContext.cacheDir.absolutePath ?: "",
            files,
            fileExtension
        )
        val filesFromFileDir: MutableList<File?> = mutableListOf()
        FileUtils.getAllNestedFiles(
            context.applicationContext.filesDir.absolutePath ?: "",
            files,
            fileExtension
        )
        files.addAll(filesFromFileDir)
        val keys = mutableListOf<String>()
        files.forEach {
            it?.let {
                val name = it.name.replace(fileExtension, "")
                keys.add(name)
            }
        }
        return keys
    }
}