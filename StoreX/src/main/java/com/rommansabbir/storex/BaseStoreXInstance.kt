package com.rommansabbir.storex

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.util.*

abstract class BaseStoreXInstance(
    private val application: Application,
    private val prefRef: String
) : StoreXStorage {
    // Storage
    private var mSharedPreferences: SharedPreferences =
        application.getSharedPreferences(prefRef, Context.MODE_PRIVATE)

    private fun getSharedPref(): SharedPreferences {
        return mSharedPreferences
    }

    protected fun notifyClients(key: String, instance: StoreX) {
        StoreXCore.subscriberList().keys.forEach { cacheKey ->
            StoreXCore.subscriberList()[cacheKey]?.let {
                if (cacheKey.contains(key) && it.getKey() == cacheKey) {
                    it.callback.onDataChanges(it, instance)
                }
            }
        }
    }

    @Throws(Exception::class)
    override fun doCache(key: String, value: String, writeToCacheDirectory: Boolean): Boolean {
        if (writeToCacheDirectory) {
            return doCacheToCacheDirectory(key, value)
        }
        getSharedPref().edit().putString(key, value).apply()
        return true
    }

    private fun doCacheToCacheDirectory(key: String, value: String): Boolean {
        //Create new file
        val file = File(application.cacheDir, key)
        //If file already exists, delete the previous one and store the new one
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()

        //Write the file to the cache dir
        val fos = FileOutputStream(file)
        fos.write(value.toByteArray(Charset.defaultCharset()))
        fos.flush()
        fos.close()
        return true
    }

    @Throws(Exception::class)
    override fun getCache(key: String, getFromCacheDirectory: Boolean): String? {
        if (getFromCacheDirectory) {
            return getCacheFromCacheDirectory(key)
        }
        return getSharedPref().getString(key, null)
    }

    private fun getCacheFromCacheDirectory(key: String): String? {
        val file = File(application.cacheDir, key)
        //If file exists decode the file to String else return null
        return if (file.exists()) {
            val content: StringBuilder = java.lang.StringBuilder(file.length().toInt())
            val scanner = Scanner(file)
            while (scanner.hasNext()) {
                content.append(scanner.nextLine() + System.lineSeparator())
            }
            content.toString()
        } else {
            null
        }
    }

    internal fun clearCacheFromCacheDir(key: String) {
        application.cacheDir?.listFiles()?.find { it.name == key }?.delete()
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
}