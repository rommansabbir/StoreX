package com.rommansabbir.storex.v2.objectwritter

interface ObjectWriter {
    fun writeObject(path: String, fileName: String, objectToBeSaved: String, overwriteExistingFile : Boolean): Boolean
    fun getWrittenObject(path: String, fileName: String): String
    fun deleteWrittenObject(path: String, fileName: String): Boolean
}
