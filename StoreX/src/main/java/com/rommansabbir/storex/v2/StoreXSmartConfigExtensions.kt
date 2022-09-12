package com.rommansabbir.storex.v2

import com.rommansabbir.storex.StoreAbleObject

inline fun <T : StoreAbleObject> StoreXSmartConfig<T>.getCacheDir(crossinline onPath: (String) -> Unit = {}): String {
    val path = ((storeXCachingStrategy as StoreXCachingStrategy.CacheDir).path?.let
    { return@let "${this.context.get()?.applicationContext?.cacheDir}/${it}" }
        ?: kotlin.run { this.context.get()?.applicationContext?.cacheDir }?.absolutePath
        ?: "")
    onPath.invoke(path)
    return path
}