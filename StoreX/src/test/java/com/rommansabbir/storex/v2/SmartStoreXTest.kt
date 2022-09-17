package com.rommansabbir.storex.v2

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.smartstorex.SmartStoreX
import com.rommansabbir.storex.v2.smartstorex.SmartStoreXImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class SmartStoreXTest : BaseTestClass() {
    private lateinit var smartStoreX: SmartStoreX
    private lateinit var cacheDirConfig: StoreXSmartConfig<StoreAbleObject>
    private lateinit var filesDirConfig: StoreXSmartConfig<StoreAbleObject>
    private lateinit var externalDirConfig: StoreXSmartConfig<StoreAbleObject>

    @Before
    fun setup() {
        smartStoreX = SmartStoreXImpl()
        cacheDirConfig = ConfigUtils.getTestCacheDirConfig(WeakReference(context))
        filesDirConfig = ConfigUtils.getTestFilesDirConfig(WeakReference(context))
        externalDirConfig = ConfigUtils.getTestFilesExternalConfig(WeakReference(context))
    }

    @Test
    fun `save a test object to internal cache dir, get stored object and delete the object finally`() {
        val uid = cacheDirConfig.xObject.objectId
        val result = smartStoreX.set(cacheDirConfig)
        assert(result)
        val returnedResult = smartStoreX.get(
            cacheDirConfig,
            StoreAbleObject::class.java
        )
        assert(returnedResult is StoreAbleObject)
        assert(returnedResult?.objectId == uid)
        val isDeleted = smartStoreX.delete(cacheDirConfig)
        assert(isDeleted)
    }

    @Test
    fun `save a test object to internal files dir, get stored object and delete the object finally`() {
        val uid = filesDirConfig.xObject.objectId
        val result = smartStoreX.set(filesDirConfig)
        assert(result)
        val returnedResult = smartStoreX.get(
            filesDirConfig,
            StoreAbleObject::class.java
        )
        assert(returnedResult is StoreAbleObject)
        assert(returnedResult?.objectId == uid)
        val isDeleted = smartStoreX.delete(filesDirConfig)
        assert(isDeleted)
    }
}