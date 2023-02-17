package com.rommansabbir.storex.v2

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.extensions.getCacheDir
import com.rommansabbir.storex.v2.extensions.toJson
import com.rommansabbir.storex.v2.extensions.toStoreAbleObject
import com.rommansabbir.storex.v2.objectwritter.ObjectWriter
import com.rommansabbir.storex.v2.objectwritter.ObjectWriterImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class ObjectWriterTest : BaseTestClass() {
    private lateinit var objectWriter: ObjectWriter
    private lateinit var testConfig: StoreXSmartConfig<StoreAbleObject>

    @Before
    fun setup() {
        objectWriter = ObjectWriterImpl()
        testConfig = ConfigUtils.getTestCacheDirConfig(WeakReference(context))
    }

    @Test
    fun `write a test object`() {
        val result = objectWriter.writeObject(
            testConfig.getCacheDir(),
            testConfig.fileName,
            testConfig.xObject.toJson(),
            true
        )
        assert(result)
    }

    @Test
    fun `get written object`() {
        `write a test object`()
        val writtenObject =
            objectWriter.getWrittenObject(testConfig.getCacheDir(), testConfig.fileName)
        val storedObject = writtenObject.toStoreAbleObject(StoreAbleObject::class.java)
        assert(storedObject.objectId == testConfig.xObject.objectId)
    }

    @Test
    fun `delete written object`() {
        `get written object`()
        val result =
            objectWriter.deleteWrittenObject(testConfig.getCacheDir(), testConfig.fileName)
        assert(result)
    }
}