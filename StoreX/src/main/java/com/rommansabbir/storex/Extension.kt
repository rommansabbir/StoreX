package com.rommansabbir.storex
import com.google.gson.Gson
fun storeXInstance(config: StoreXConfig): StoreX = StoreXCore.instance(config)

internal fun Subscriber.getKey(): String {
    return "${this.key}_${this.subscriberID}"
}

val gson by lazy { Gson() }