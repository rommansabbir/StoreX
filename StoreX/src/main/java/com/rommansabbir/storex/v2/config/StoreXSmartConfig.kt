package com.rommansabbir.storex.v2.config

import android.content.Context
import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.strategy.StoreXCachingStrategy
import java.lang.ref.WeakReference

data class StoreXSmartConfig<T : StoreAbleObject>(
    val context: WeakReference<Context>,
    val fileName: String,
    val xObject: T,
    val storeXCachingStrategy: StoreXCachingStrategy,
    val overwriteExistingFile: Boolean = true
)