package com.rommansabbir.storex

@Deprecated("Use new method StoreXCore.instance(config: StoreXConfig) to get specific instance", ReplaceWith("StoreXCore.instance(config: StoreXConfig)"))
fun storeXInstance(): StoreX = StoreXCore.instance()

fun storeXInstance(config: StoreXConfig): StoreX = StoreXCore.instance(config)

internal fun Subscriber.getKey(): String {
    return "${this.key}_${this.subscriberID}"
}