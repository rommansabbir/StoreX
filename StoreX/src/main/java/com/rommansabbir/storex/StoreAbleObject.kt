package com.rommansabbir.storex

import java.io.Serializable
import java.util.*

open class StoreAbleObject : Serializable {
    private val _objectId: String = UUID.randomUUID().toString()
    val objectId: String
        get() = _objectId

    fun isSameObject(storeAbleObject: StoreAbleObject): Boolean =
        storeAbleObject._objectId == _objectId
}
