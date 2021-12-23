package com.rommansabbir.storex

import android.app.Application
import com.google.gson.Gson
import com.rommansabbir.storex.execptions.DuplicateStoreXConfigException
import com.rommansabbir.storex.execptions.InvalidEncryptionKeyException
import com.rommansabbir.storex.execptions.NoConfigFoundException
import com.rommansabbir.storex.execptions.NotInitializedException

object StoreXCore {
    private var isInitialized: Boolean = false
    private var instance: StoreXInstance? = null

    private var instanceStates: HashMap<String, StoreXState> = HashMap()

    /**
     * When you go crazy and want to delete all the cache, call this method.
     */
    fun clearAllStates() {
        instanceStates.clear()
    }

    @Deprecated(
        "Use new method StoreXCore.instance(config: StoreXConfig) to get specific instance",
        ReplaceWith("StoreXCore.instance(config: StoreXConfig)")
    )
    @Throws(RuntimeException::class)
    fun instance(): StoreX {
        if (isInitialized) {
            return instance!!
        } else {
            throw NotInitializedException()
        }
    }

    @Throws(RuntimeException::class)
    fun instance(configs: StoreXConfig): StoreX {
        if (isInitialized) {
            instanceStates[configs.uId]?.let {
                return it.storeXInstance
            } ?: run {
                throw NoConfigFoundException()
            }
        } else {
            throw NotInitializedException()
        }
    }

    @Throws(RuntimeException::class)
    fun init(application: Application, configs: MutableList<StoreXConfig>) {
        val gson = Gson()
        configs.forEach {
            if (instanceStates[it.uId] != null) {
                throw DuplicateStoreXConfigException()
            }
            instanceStates[it.uId] = StoreXState(
                StoreXInstance(
                    application,
                    it.prefName,
                    gson,
                    it.writeOrGetAsFileUsingCacheDirectory
                ), it
            )
        }
        this.isInitialized = true
    }

    @Deprecated(
        "Use new method StoreXCore.init(application: Application, configs: MutableList<StoreXConfig>) initialize properly",
        ReplaceWith("StoreXCore.init(application: Application, configs: MutableList<StoreXConfig>)")
    )
    fun init(application: Application, prefName: String, writeOrGetAsFileUsingCacheDirectory : Boolean = false): StoreXCore {
        this.instance = StoreXInstance(application, prefName, Gson(), writeOrGetAsFileUsingCacheDirectory)
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