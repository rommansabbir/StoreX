package com.rommansabbir.storex.v2.smartstorex

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.subscription.StoreXSubscription

interface SmartStoreX {
    @Throws(Exception::class)
    fun <T : StoreAbleObject> set(config: StoreXSmartConfig<T>): Boolean

    @Throws(Exception::class)
    fun <T : StoreAbleObject> get(config: StoreXSmartConfig<T>, clazzName: Class<T>): T?

    @Throws(Exception::class)
    fun <T : StoreAbleObject> delete(config: StoreXSmartConfig<T>): Boolean

    companion object {
        private val instance: SmartStoreXImpl by lazy { SmartStoreXImpl() }

        internal val subscriberList: HashMap<String, StoreXSubscription> =
            hashMapOf()

        fun registerSubscriber(
            key: String, subscriber: StoreXSubscription
        ) {
            try {
                subscriberList[key] = subscriber
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun removeSubscriber(key: String) {
            try {
                subscriberList.remove(key)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun removeAllSubscriber() {
            subscriberList.keys.forEach {
                subscriberList.remove(it)
            }
        }

        val getInstance: SmartStoreX
            get() = instance
    }
}

