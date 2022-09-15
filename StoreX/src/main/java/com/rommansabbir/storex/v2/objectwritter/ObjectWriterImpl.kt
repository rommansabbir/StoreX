package com.rommansabbir.storex.v2.objectwritter

import com.rommansabbir.storex.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.util.*

class ObjectWriterImpl : ObjectWriter {
    override fun writeObject(path: String, fileName: String, objectToBeSaved: String): Boolean {
        try {
            val file = File(path, "${fileName}.obj")
            if (file.exists()) {
                FileUtils.deleteQuietly(file)
            }
            file.createNewFile()
            val fos = FileOutputStream(file)
            fos.write(objectToBeSaved.toByteArray(Charset.defaultCharset()))
            fos.flush()
            fos.close()
            return true
        } catch (e: Exception) {
            val message = "Failed to write the object : '$fileName' to path : '$path'"
            throw Exception(message)
        }
    }

    override fun getWrittenObject(path: String, fileName: String): String {
        try {
            val file = File(path, "${fileName}.obj")
            val content: StringBuilder = java.lang.StringBuilder(file.length().toInt())
            val scanner = Scanner(file)
            while (scanner.hasNext()) {
                content.append(scanner.nextLine() + System.lineSeparator())
            }
            return content.toString()
        } catch (e: Exception) {
            val message = "Failed to get written object : '$fileName from path' : '$path'"
            throw Exception(message)
        }
    }

    override fun deleteWrittenObject(path: String, fileName: String): Boolean {
        try {
            val file = File("${path}/${fileName}.obj")
            if (file.isDirectory) {
                FileUtils.deleteDirectory(file)
                return true
            }
            FileUtils.deleteQuietly(file)
            return true
        } catch (e: Exception) {
            val message = "Failed to delete written object : '$fileName' from path : '$path'"
            throw Exception(message)
        }
    }

}