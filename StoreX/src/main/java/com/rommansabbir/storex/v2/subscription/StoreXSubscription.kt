package com.rommansabbir.storex.v2.subscription

import com.rommansabbir.storex.StoreAbleObject

interface StoreXSubscription {
    fun <T : StoreAbleObject> onCallback(fileName: String, updatedObject: T)
    fun <T : StoreAbleObject> onDelete(fileName: String, deletedObject : T){}
}