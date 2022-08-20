package com.rommansabbir.storex.callbacks

import com.rommansabbir.storex.StoreAbleObject

@Deprecated("Will be removed in future version.")
interface SaveCallback<T : StoreAbleObject> {
    fun onDone(value : T, exception: Exception? = null)
}