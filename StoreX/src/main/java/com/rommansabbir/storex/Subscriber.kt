package com.rommansabbir.storex

import com.rommansabbir.storex.callbacks.EventCallback

data class Subscriber(
    val key: String,
    val subscriberID: Int,
    val callback: EventCallback
)

