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

    @Throws(Exception::class)
    override fun doCache(key: String, value: String, writeToCacheDirectory: Boolean): Boolean {
        if (writeToCacheDirectory) {
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
        getSharedPref().edit().putString(key, value).apply()
        return true
    }

    @Throws(Exception::class)
    override fun getCache(key: String, getFromCacheDirectory: Boolean): String? {
        if (getFromCacheDirectory) {
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
}