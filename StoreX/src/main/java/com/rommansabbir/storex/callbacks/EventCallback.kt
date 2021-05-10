package com.rommansabbir.storex.callbacks

import com.rommansabbir.storex.StoreX
import com.rommansabbir.storex.Subscriber

interface EventCallback {
    fun onDataChanges(
        subscriber: Subscriber,
        instance: StoreX
    )
}