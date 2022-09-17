package com.rommansabbir.storex.v2.smartstorex

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig

interface SmartStoreX {
    @Throws(Exception::class)
    fun <T : StoreAbleObject> set(config: StoreXSmartConfig<T>): Boolean

    @Throws(Exception::class)
    fun <T : StoreAbleObject> get(config: StoreXSmartConfig<T>, clazzName: Class<T>): T?

    @Throws(Exception::class)
    fun <T : StoreAbleObject> delete(config: StoreXSmartConfig<T>): Boolean
}

