package com.rommansabbir.storex.v2.strategy

import java.io.File

sealed class StoreXCachingStrategy {
    object CacheDir : StoreXCachingStrategy()
    object FilesDir : StoreXCachingStrategy()
    class OtherDir(val externalStorageFile: File) :
        StoreXCachingStrategy()
    companion object{
        fun getTag(obj: StoreXCachingStrategy): String {
            return when (obj) {
                CacheDir -> "CacheDir"
                FilesDir -> "FilesDir"
                is OtherDir -> "OtherDir"
            }
        }
    }
}