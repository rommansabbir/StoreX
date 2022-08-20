package com.rommansabbir.storex

import com.rommansabbir.storex.callbacks.GetCallback
import com.rommansabbir.storex.callbacks.SaveCallback
import kotlinx.coroutines.CoroutineScope

interface StoreX {
    fun put(key: String, value: StoreAbleObject): Boolean

    @Deprecated(
        "Use new suspended API",
        replaceWith = ReplaceWith("StoreXCore.instance(config).putS(key, value)")
    )
    fun put(scope: CoroutineScope, key: String, value: StoreAbleObject)

    suspend fun putS(key: String, value: StoreAbleObject): Boolean

    @Deprecated(
        "Use new suspended API",
        replaceWith = ReplaceWith("StoreXCore.instance(config).putS(key, value)")
    )
    fun <T : StoreAbleObject> put(key: String, value: StoreAbleObject, callback: SaveCallback<T>)

    @Deprecated(
        "Use new suspended API",
        replaceWith = ReplaceWith("StoreXCore.instance(config).putS(key, value)")
    )
    fun <T : StoreAbleObject> put(
        scope: CoroutineScope,
        key: String,
        value: StoreAbleObject,
        callback: SaveCallback<T>
    )

    fun <T : StoreAbleObject> get(key: String, objectType: Class<T>): T

    fun <T : StoreAbleObject> get(key: String, objectType: Class<T>, callback: GetCallback<T>)

    fun <T : StoreAbleObject> get(
        scope: CoroutineScope,
        key: String,
        objectType: Class<T>,
        callback: GetCallback<T>
    )

    fun addSubscriber(subscriber: Subscriber)

    fun addSubscriber(subscribers: List<Subscriber>)

    fun removeSubscriber(subscriber: Subscriber)

    fun removeSubscriber(subscribers: List<Subscriber>)

    fun remove(key: String)

    fun removeFromCacheDir(key: List<String>): Boolean

    fun removeAll()
}