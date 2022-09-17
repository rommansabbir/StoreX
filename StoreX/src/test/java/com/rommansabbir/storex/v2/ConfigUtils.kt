package com.rommansabbir.storex.v2

import android.content.Context
import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.strategy.StoreXCachingStrategy
import java.io.File
import java.lang.ref.WeakReference

object ConfigUtils {
    fun getTestCacheDirConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile", StoreAbleObject(), StoreXCachingStrategy.CacheDir)

    fun getTestFilesDirConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile", StoreAbleObject(), StoreXCachingStrategy.FilesDir)

    fun getTestFilesExternalConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile", StoreAbleObject(), StoreXCachingStrategy.OtherDir(
            File("")
        ))
}