package com.rommansabbir.storex.v2

sealed class StoreXCachingStrategy {
    class CacheDir(val path: String? = null) : StoreXCachingStrategy()
    class FilesDir(val path: String? = null) : StoreXCachingStrategy()
    class ExternalDir(val path: String? = null) : StoreXCachingStrategy()
}