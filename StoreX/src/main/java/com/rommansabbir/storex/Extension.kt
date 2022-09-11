package com.rommansabbir.storex


fun storeXInstance(config: StoreXConfig): StoreX = StoreXCore.instance(config)

internal fun Subscriber.getKey(): String {
    return "${this.key}_${this.subscriberID}"
}