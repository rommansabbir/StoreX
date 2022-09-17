package com.rommansabbir.storex.v2.search

import android.content.Context
import com.rommansabbir.storex.FileUtils
import com.rommansabbir.storex.v2.extensions.throwException
import java.io.File
import java.lang.ref.WeakReference

internal class SmartStoreXSearchImpl : SmartStoreXSearch {
    override fun getAllKeys(context: WeakReference<Context>): MutableList<String> {
        if (context.get() == null) {
            throwException("Context can't be null")
        }
        val fileExtension = ".storexfile"
        val files: MutableList<File?> = mutableListOf()
        FileUtils.getAllNestedFiles(
            context.get()!!.applicationContext.cacheDir.absolutePath ?: "",
            files,
            fileExtension
        )
        val filesFromFileDir: MutableList<File?> = mutableListOf()
        FileUtils.getAllNestedFiles(
            context.get()!!.applicationContext.filesDir.absolutePath ?: "",
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