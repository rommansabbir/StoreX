package com.rommansabbir.storex.callbacks

import com.rommansabbir.storex.StoreAbleObject

interface SaveCallback<T : StoreAbleObject> {
    fun onDone(value : T, exception: Exception? = null)
}