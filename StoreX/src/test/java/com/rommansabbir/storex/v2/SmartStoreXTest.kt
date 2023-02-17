package com.rommansabbir.storex.v2

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.smartstorex.SmartStoreX
import com.rommansabbir.storex.v2.smartstorex.SmartStoreXImpl
import com.rommansabbir.storex.v2.subscription.StoreXSubscription
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
    fun `save a test object to internal cache dir, get stored object and delete the object finally including callback`() {
        var callbackObject: StoreAbleObject? = null
        var deletedCallback: StoreAbleObject? = null
        SmartStoreX.registerSubscriber(
            cacheDirConfig.fileName,
            object : StoreXSubscription {
                override fun <T : StoreAbleObject> onCallback(fileName: String, updatedObject: T) {
                    callbackObject = updatedObject
                }

                override fun <T : StoreAbleObject> onDelete(fileName: String, deletedObject: T) {
                    deletedCallback = deletedObject
                }
            })
        val uid = cacheDirConfig.xObject.objectId
        val result = smartStoreX.write(cacheDirConfig)
        assert(result)
        val returnedResult = smartStoreX.read(
            cacheDirConfig,
            StoreAbleObject::class.java
        )
        assert(returnedResult is StoreAbleObject)
        assert(returnedResult?.objectId == uid)
        val isDeleted = smartStoreX.delete(cacheDirConfig)
        assert(isDeleted)
        assert(callbackObject?.objectId == uid)
        assert(deletedCallback?.objectId == uid)
    }

    @Test
    fun `save a test object to internal files dir, get stored object and delete the object finally  including callback`() {
        var callbackObject: StoreAbleObject? = null
        var deletedCallback: StoreAbleObject? = null
        SmartStoreX.registerSubscriber(
            filesDirConfig.fileName,
            object : StoreXSubscription {
                override fun <T : StoreAbleObject> onCallback(fileName: String, updatedObject: T) {
                    callbackObject = updatedObject
                }

                override fun <T : StoreAbleObject> onDelete(fileName: String, deletedObject: T) {
                    deletedCallback = deletedObject
                }
            })
        val uid = filesDirConfig.xObject.objectId
        val result = smartStoreX.write(filesDirConfig)
        assert(result)
        val returnedResult = smartStoreX.read(
            filesDirConfig,
            StoreAbleObject::class.java
        )
        assert(returnedResult is StoreAbleObject)
        assert(returnedResult?.objectId == uid)
        val isDeleted = smartStoreX.delete(filesDirConfig)
        assert(isDeleted)
        assert(callbackObject?.objectId == uid)
        assert(deletedCallback?.objectId == uid)
    }

    @Test
    fun `check subscription register and remove feature`() {
        SmartStoreX.registerSubscriber(
            "test",
            object : StoreXSubscription {
                override fun <T : StoreAbleObject> onCallback(fileName: String, updatedObject: T) {}
            })
        assert(SmartStoreX.subscriberList.contains("test"))
        SmartStoreX.removeSubscriber("test")
        assert(!SmartStoreX.subscriberList.contains("test"))
    }
}