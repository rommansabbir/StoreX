package com.rommansabbir.storex.v2.extensions

import com.rommansabbir.storex.StoreAbleObject
import com.rommansabbir.storex.gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun StoreAbleObject.toJson(): String = gson.toJson(this)

fun <T : StoreAbleObject> String.toStoreAbleObject(clazz: Class<T>): T =
    gson.fromJson(this, clazz)

inline fun String.isJSONValid(crossinline callback: (Boolean) -> Unit = {}): Boolean {
    try {
        JSONObject(this)
    } catch (ex: JSONException) {
        try {
            JSONArray(this)
        } catch (ex1: JSONException) {
            callback.invoke(false)
            return false
        }
    }
    callback.invoke(true)
    return true
}