package com.rommansabbir.storex.callbacks

import com.rommansabbir.storex.StoreAbleObject

interface GetCallback<T : StoreAbleObject> {
    fun onSuccess(value: T? = null, exception: Exception? = null)
}