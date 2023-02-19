package com.rommansabbir.storex.v2.search

import android.content.Context
import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.smartstorex.SmartStoreX
import com.rommansabbir.storex.v2.strategy.StoreXCachingStrategy
import java.lang.ref.WeakReference

/**
 * Search for all cached files or check if contain file stored with [SmartStoreX].
 *
 * Note: Search only applicable for [StoreXCachingStrategy.CacheDir] and [StoreXCachingStrategy.FilesDir].
 */
internal interface SmartStoreXSearch {
    /**
     * Get all [StoreAbleObject] file name from [StoreXCachingStrategy.CacheDir]
     * and [StoreXCachingStrategy.FilesDir].
     *
     * @param context [WeakReference]<[Context]>.
     *
     * @return [MutableList]<[String]>
     */
    fun getAll(context: WeakReference<Context>): MutableList<String>

    /**
     * Check if a [StoreAbleObject] exist the given file name name
     * from [StoreXCachingStrategy.CacheDir] and [StoreXCachingStrategy.FilesDir].
     *
     * @param [fileName] File name to be searched for.
     * @param [context] [WeakReference]<[Context]>.
     *
     * @return [Boolean]
     */
    fun exist(fileName: String, context: WeakReference<Context>): Boolean
}

