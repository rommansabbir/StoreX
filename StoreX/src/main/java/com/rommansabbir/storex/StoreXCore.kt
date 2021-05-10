package com.rommansabbir.storex

import android.app.Application
import com.google.gson.Gson
import com.rommansabbir.storex.execptions.InvalidEncryptionKeyException
import com.rommansabbir.storex.execptions.NotInitializedException

object StoreXCore {
    private var isInitialized: Boolean = false
    private var instance: StoreXInstance? = null

    @Throws(RuntimeException::class)
    fun instance(): StoreX {
        if (isInitialized) {
            return instance!!
        } else {
            throw NotInitializedException()
        }
    }

    fun init(application: Application, prefName: String): StoreXCore {
        this.instance = StoreXInstance(application, prefName, Gson())
        this.instance!!.registerListener(this.instance!!.listener)
        this.isInitialized = true
        return this
    }

    @Throws(RuntimeException::class)
    fun setEncryptionKey(key: String) {
        if (key.isEmpty()) {
            throw InvalidEncryptionKeyException()
        }
        this.encryptionKey = key
    }

    internal const val NO_ENCRYPTION = "NoEncryption"
    internal var encryptionKey: String = NO_ENCRYPTION

    //
    private var subscriberListMain: HashMap<String, Subscriber> = HashMap()
    internal fun subscriberList(): HashMap<String, Subscriber> {
        return subscriberListMain
    }

    internal fun addSubscriber(subscriber: Subscriber) {
        subscriberListMain[subscriber.getKey()] = subscriber
    }

    internal fun removeSubscriber(subscriber: Subscriber) {
        val key = subscriber.getKey()
        if (subscriberListMain.containsKey(key)) {
            subscriberListMain.remove(key)
        }
    }
}