package com.rommansabbir.storex.v2.smartstorex

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.*
import com.rommansabbir.storex.v2.objectwritter.ObjectWriter
import com.rommansabbir.storex.v2.objectwritter.ObjectWriterImpl

class SmartStoreXImpl : SmartStoreX {
    private var objectWriter: ObjectWriter = ObjectWriterImpl()

    override fun <T : StoreAbleObject> set(config: StoreXSmartConfig<T>): Boolean {
        val path = getPath(config)
        return objectWriter.writeObject(path, config.fileName, config.xObject.toJson())
    }

    override fun <T : StoreAbleObject> get(
        config: StoreXSmartConfig<T>,
        clazzName: Class<T>
    ): T? {
        val path = getPath(config)
        return try {
            objectWriter.getWrittenObject(path, config.fileName).toStoreAbleObject(clazzName)
        } catch (e: Exception) {
            null
        }
    }

    override fun <T : StoreAbleObject> delete(config: StoreXSmartConfig<T>): Boolean {
        val path = getPath(config)
        objectWriter.deleteWrittenObject(path, config.fileName)
        return true
    }

    private fun <T : StoreAbleObject> getPath(config: StoreXSmartConfig<T>): String {
        var tag: String = ""
        try {
            return when (config.storeXCachingStrategy) {
                is StoreXCachingStrategy.CacheDir -> {
                    tag = "CacheDir"; config.getCacheDir()
                }
                is StoreXCachingStrategy.FilesDir -> {
                    tag = "FilesDir";config.getFilesDir()
                }
                is StoreXCachingStrategy.ExternalDir -> {
                    tag = "ExternalDir";config.getExternalDir()
                }
            }
        } catch (e: Exception) {
            throw Exception("Failed to get path for :'${tag}'")
        }
    }

}