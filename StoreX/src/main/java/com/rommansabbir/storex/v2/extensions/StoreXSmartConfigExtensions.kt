package com.rommansabbir.storex.v2.extensions

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.strategy.StoreXCachingStrategy


inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getCacheDir(crossinline onPath: (String) -> Unit = {}): String {
    if (this.context.get() == null) {
        throwException("Context can't be null")
    }
    val path = this.context.get()!!.applicationContext?.cacheDir?.absolutePath ?: ""
    onPath.invoke(path)
    return path
}

inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getFilesDir(crossinline onPath: (String) -> Unit = {}): String {
    if (this.context.get() == null) {
        throwException("Context can't be null")
    }
    val path = this.context.get()!!.applicationContext?.filesDir?.absolutePath ?: ""
    onPath.invoke(path)
    return path
}

inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getOtherDir(crossinline onPath: (String) -> Unit = {}): String {
    if (this.context.get() == null) {
        throwException("Context can't be null")
    }
    val path =
        (this.storeXCachingStrategy as StoreXCachingStrategy.OtherDir).externalStorageFile.absolutePath
            ?: ""
    onPath.invoke(path)
    return path
}

fun throwException(message: String) {
    throw Exception(message)
}