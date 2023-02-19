package com.rommansabbir.storex.v2.search

import android.content.Context
import com.rommansabbir.storex.v2.extensions.throwException
import java.io.File
import java.lang.ref.WeakReference

internal class SmartStoreXSearchImpl : SmartStoreXSearch {
    override fun getAll(context: WeakReference<Context>): MutableList<String> {
        if (context.get() == null) {
            throwException("Context can't be null")
        }
        val fileExtension = ".storexfile"
        val files: MutableList<File?> = searchStoreAbleObject(context.get()!!.applicationContext.cacheDir.absolutePath ?: "").apply {
            addAll(searchStoreAbleObject(context.get()!!.applicationContext.filesDir.absolutePath ?: ""))
        }
        val keys = mutableListOf<String>()
        files.forEach {
            it?.let {
                val name = it.name.replace(fileExtension, "")
                keys.add(name)
            }
        }
        return keys
    }

    override fun exist(fileName: String, context: WeakReference<Context>): Boolean {
        return getAll(context).contains(fileName)
    }
}