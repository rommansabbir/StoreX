package com.rommansabbir.storex.v2

import android.os.Environment
import com.rommansabbir.storex.StoreAbleObject


internal inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getCacheDir(crossinline onPath: (String) -> Unit = {}): String {
    if (this.context.get() == null) {
        throwException("Context can't be null")
    }
    val path = ((storeXCachingStrategy as StoreXCachingStrategy.CacheDir).path?.let
    { return@let "${this.context.get()!!.applicationContext?.cacheDir}/${it}" }
        ?: kotlin.run { this.context.get()!!.applicationContext?.cacheDir }?.absolutePath
        ?: "")
    onPath.invoke(path)
    return path
}

inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getFilesDir(crossinline onPath: (String) -> Unit = {}): String {
    if (this.context.get() == null) {
        throwException("Context can't be null")
    }
    val path = ((storeXCachingStrategy as StoreXCachingStrategy.FilesDir).path?.let
    { return@let "${this.context.get()!!.applicationContext?.filesDir}/${it}" }
        ?: kotlin.run { this.context.get()!!.applicationContext?.filesDir }?.absolutePath
        ?: "")
    onPath.invoke(path)
    return path
}

inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getExternalDir(crossinline onPath: (String) -> Unit = {}): String {
    if (this.context.get() == null) {
        throwException("Context can't be null")
    }
    if (!isExternalStorageAvailable()) {
        throwException("'External Storage' is not available")
    }
    if (isExternalStorageReadOnly()) {
        throwException("'External Storage' is in `Read Only` mode")
    }
    val path = ((storeXCachingStrategy as StoreXCachingStrategy.ExternalDir).path?.let
    { return@let "${this.context.get()!!.applicationContext?.getExternalFilesDir("Android/cache")}/${it}" }
        ?: "")
    onPath.invoke(path)
    return path
}

fun throwException(message: String) {
    throw Exception(message)
}

fun isExternalStorageReadOnly(): Boolean {
    val extStorageState = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
}

fun isExternalStorageAvailable(): Boolean {
    val extStorageState = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == extStorageState
}