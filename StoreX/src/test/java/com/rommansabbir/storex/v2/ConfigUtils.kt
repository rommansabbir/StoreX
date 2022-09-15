package com.rommansabbir.storex.v2

import android.content.Context
import com.rommansabbir.storex.StoreAbleObject
import java.lang.ref.WeakReference

object ConfigUtils {
    fun getTestCacheDirConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile", StoreAbleObject(), StoreXCachingStrategy.CacheDir())

    fun getTestFilesDirConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile", StoreAbleObject(), StoreXCachingStrategy.FilesDir())

    fun getTestFilesExternalConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile", StoreAbleObject(), StoreXCachingStrategy.ExternalDir())
}