package com.rommansabbir.storex.v2.smartstorex

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.extensions.*
import com.rommansabbir.storex.v2.objectwritter.ObjectWriter
import com.rommansabbir.storex.v2.objectwritter.ObjectWriterImpl
import com.rommansabbir.storex.v2.strategy.StoreXCachingStrategy

class SmartStoreXImpl : SmartStoreX {

    private val objectWriter: ObjectWriter by lazy { ObjectWriterImpl() }

    override fun <T : StoreAbleObject> set(config: StoreXSmartConfig<T>): Boolean {
        val path = getPath(config)
        val hasWritten = objectWriter.writeObject(
            path,
            config.fileName,
            config.xObject.toJson(),
            config.overwriteExistingFile
        )
        SmartStoreX.subscriberList[config.fileName]?.onCallback(config.fileName, config.xObject)
        return hasWritten
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
        SmartStoreX.subscriberList[config.fileName]?.onDelete(config.fileName, config.xObject)
        return true
    }

    private fun <T : StoreAbleObject> getPath(config: StoreXSmartConfig<T>): String {
        try {
            return when (config.storeXCachingStrategy) {
                is StoreXCachingStrategy.CacheDir -> {
                    config.getCacheDir()
                }
                is StoreXCachingStrategy.FilesDir -> {
                    config.getFilesDir()
                }
                is StoreXCachingStrategy.OtherDir -> {
                    config.getOtherDir()
                }
            }
        } catch (e: Exception) {
            throw Exception("Failed to get path for :'${StoreXCachingStrategy.getTag(config.storeXCachingStrategy)}'")
        }
    }

}