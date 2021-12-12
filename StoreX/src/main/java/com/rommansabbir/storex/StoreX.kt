package com.rommansabbir.storex

import com.rommansabbir.storex.callbacks.GetCallback
import com.rommansabbir.storex.callbacks.SaveCallback
import kotlinx.coroutines.CoroutineScope

interface StoreX {
    @Throws(RuntimeException::class)
    fun put(key: String, value: StoreAbleObject): Boolean

    @Throws(RuntimeException::class)
    fun put(scope : CoroutineScope, key: String, value: StoreAbleObject)

    fun <T : StoreAbleObject> put(key: String, value: StoreAbleObject, callback: SaveCallback<T>)

    fun <T : StoreAbleObject> put(scope : CoroutineScope,key: String, value: StoreAbleObject, callback: SaveCallback<T>)

    @Throws(RuntimeException::class)
    fun <T : StoreAbleObject> get(key: String, objectType: Class<T>): T

    fun <T : StoreAbleObject> get(key: String, objectType: Class<T>, callback: GetCallback<T>)

    fun <T : StoreAbleObject> get(scope : CoroutineScope,key: String, objectType: Class<T>, callback: GetCallback<T>)

    @Throws(RuntimeException::class)
    fun addSubscriber(subscriber: Subscriber)

    @Throws(RuntimeException::class)
    fun addSubscriber(subscribers: ArrayList<Subscriber>)

    fun removeSubscriber(subscriber: Subscriber)

    fun removeSubscriber(subscribers: ArrayList<Subscriber>)

    fun remove(key: String)

    fun removeAll()
}