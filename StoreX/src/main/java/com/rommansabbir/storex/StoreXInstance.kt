@file:Suppress("UNCHECKED_CAST")

package com.rommansabbir.storex

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rommansabbir.storex.callbacks.GetCallback
import com.rommansabbir.storex.callbacks.SaveCallback
import com.rommansabbir.storex.execptions.DuplicateKeyFoundException
import com.rommansabbir.storex.execptions.NoStoreAbleObjectFound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal class StoreXInstance(
    private val application: Application,
    private val prefRef: String,
    private val serializer: Gson
) : StoreX, StoreXStorage {
    internal var listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            StoreXCore.subscriberList().keys.forEach { cacheKey ->
                StoreXCore.subscriberList()[cacheKey]?.let {
                    if (cacheKey.contains(key) && it.getKey() == cacheKey) {
                        it.callback.onDataChanges(it, this@StoreXInstance)
                    }
                }
            }
        }

    override fun put(key: String, value: StoreAbleObject): Boolean {
        return try {
            val serializedValue: String = serializer.toJson(value)
            if (StoreXCore.encryptionKey == StoreXCore.NO_ENCRYPTION) {
                doCache(key, serializedValue)
                true
            } else {
                val encryptedValue = EncryptionTool.encrypt(serializedValue)
                doCache(key, encryptedValue)
                true
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun <T : StoreAbleObject> put(
        key: String,
        value: StoreAbleObject,
        callback: SaveCallback<T>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val serializedValue: String = serializer.toJson(value)
                if (StoreXCore.encryptionKey == StoreXCore.NO_ENCRYPTION) {
                    doCache(key, serializedValue)
                    callback.onDone(value as T, null)
                } else {
                    val encryptedValue = EncryptionTool.encrypt(serializedValue)
                    doCache(key, encryptedValue)
                    callback.onDone(value as T, null)
                }
            } catch (e: Exception) {
                callback.onDone(value as T, e)
            }
        }
    }


    override fun <T : StoreAbleObject> get(key: String, objectType: Class<T>): T {
        try {
            when (val value = getCache(key)) {
                null -> {
                    throw NoStoreAbleObjectFound()
                }
                else -> {
                    return when (StoreXCore.encryptionKey == StoreXCore.NO_ENCRYPTION) {
                        true -> {
                            serializer.fromJson(value, objectType)
                        }
                        else -> {
                            val decryptedValue = EncryptionTool.decrypt(value)
                            serializer.fromJson(decryptedValue, objectType)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }


    override fun <T : StoreAbleObject> get(
        key: String,
        objectType: Class<T>,
        callback: GetCallback<T>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when (val value = getCache(key)) {
                    null -> {
                        callback.onSuccess(null, NoStoreAbleObjectFound())
                    }
                    else -> {
                        when (StoreXCore.encryptionKey == StoreXCore.NO_ENCRYPTION) {
                            true -> {
                                callback.onSuccess(serializer.fromJson(value, objectType))
                            }
                            else -> {
                                val decryptedValue = EncryptionTool.decrypt(value)
                                callback.onSuccess(serializer.fromJson(decryptedValue, objectType))
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                callback.onSuccess(null, e)
            }
        }
    }

    override fun addSubscriber(subscriber: Subscriber) {
        if (StoreXCore.subscriberList().containsKey(subscriber.getKey())) {
            throw DuplicateKeyFoundException()
        }
        StoreXCore.addSubscriber(subscriber)
    }

    override fun addSubscriber(subscribers: ArrayList<Subscriber>) {
        subscribers.forEach {
            addSubscriber(it)
        }
    }

    override fun removeSubscriber(subscriber: Subscriber) {
        StoreXCore.removeSubscriber(subscriber)
    }

    override fun removeSubscriber(subscribers: ArrayList<Subscriber>) {
        subscribers.forEach {
            removeSubscriber(it)
        }
    }

    override fun remove(key: String) {
        clearCacheByKey(key)
    }

    override fun removeAll() {
        clearAllCache()
    }


    // Storage
    private var mSharedPreferences: SharedPreferences =
        application.getSharedPreferences(prefRef, Context.MODE_PRIVATE)

    override fun doCache(key: String, value: String): Boolean {
        getSharedPref().edit().putString(key, value).apply()
        return true
    }

    override fun getCache(key: String): String? {
        return getSharedPref().getString(key, null)
    }

    override fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun clearCacheByKey(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    override fun clearAllCache() {
        mSharedPreferences.edit().clear().apply()
    }

    private fun getSharedPref(): SharedPreferences {
        return mSharedPreferences
    }
}