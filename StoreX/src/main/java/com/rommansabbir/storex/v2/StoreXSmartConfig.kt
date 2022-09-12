package com.rommansabbir.storex.v2

import android.content.Context
import com.rommansabbir.storex.StoreAbleObject
import java.lang.ref.WeakReference

data class StoreXSmartConfig<T : StoreAbleObject>(
    val context: WeakReference<Context>,
    val fileName: String,
    val xObject: T,
    val storeXCachingStrategy: StoreXCachingStrategy
)