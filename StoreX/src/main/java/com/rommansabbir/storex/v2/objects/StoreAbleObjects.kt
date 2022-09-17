package com.rommansabbir.storex.v2.objects

import com.rommansabbir.storex.StoreAbleObject

class StoreAbleString(val value: String) : StoreAbleObject()

fun String.toStoreAbleString() = StoreAbleString(this)

class StoreAbleInt(val value: Int) : StoreAbleObject()

fun Int.toStoreAbleInt() = StoreAbleInt(this)

class StoreAbleDouble(val value: Double) : StoreAbleObject()

fun Double.toStoreAbleDouble() = StoreAbleDouble(this)

class StoreAbleLong(val value: Long) : StoreAbleObject()

fun Long.toStoreAbleLong() = StoreAbleLong(this)

class StoreAbleByte(val value: Byte) : StoreAbleObject()

fun Byte.toStoreAbleByte() = StoreAbleByte(this)

class StoreAbleChar(val value: Char) : StoreAbleObject()

fun Char.toStoreAbleChar() = StoreAbleChar(this)

class StoreAbleShort(val value: Short) : StoreAbleObject()

fun Short.toStoreAbleShort() = StoreAbleShort(this)

class StoreAbleArrayList<T>(val value: ArrayList<T>) : StoreAbleObject()

fun <T> ArrayList<T>.toStoreAbleArrayList() = StoreAbleArrayList(this)

class StoreAbleMutableList<T>(val value: MutableList<T>) : StoreAbleObject()

fun <T> MutableList<T>.toStoreAbleMutableList() = StoreAbleMutableList(this)

class StoreAbleSet<T>(val value: Set<T>) : StoreAbleObject()

fun <T> Set<T>.toStoreAbleSet() = StoreAbleSet(this)

class StoreAbleMutableSet<T>(val value: MutableSet<T>) : StoreAbleObject()

fun <T> MutableSet<T>.toStoreAbleMutableSet() =
    StoreAbleMutableSet(this)

class StoreAbleHashMap<K, V>(val value: HashMap<K, V>) : StoreAbleObject()

fun <K, V> HashMap<K, V>.toStoreAbleHashMap() = StoreAbleHashMap(this)

class StoreAbleHashSet<T>(value: HashSet<T>) : StoreAbleObject()

fun <T> HashSet<T>.toStoreAbleHashSet() = StoreAbleHashSet(this)