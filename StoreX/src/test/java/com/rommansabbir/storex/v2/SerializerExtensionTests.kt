package com.rommansabbir.storex.v2

import com.rommansabbir.storex.StoreAbleObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SerializerExtensionTests {

    @Test
    fun `convert a store able object to json object`() {
        val testObject = StoreAbleObject()
        val jsonObject = testObject.toJson()
        assert(jsonObject.isJSONValid())
    }

    @Test
    fun `convert json object to store able object`() {
        val testObject = StoreAbleObject()
        val jsonObject = testObject.toJson()
        assert(jsonObject.isJSONValid())
        val finalObject = jsonObject.toStoreAbleObject(StoreAbleObject::class.java)
        assert(finalObject.objectId == testObject.objectId)
    }
}