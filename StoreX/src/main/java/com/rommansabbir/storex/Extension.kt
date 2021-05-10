package com.rommansabbir.storex

fun storeXInstance(): StoreX = StoreXCore.instance()

internal fun Subscriber.getKey(): String {
    return "${this.key}_${this.subscriberID}"
}