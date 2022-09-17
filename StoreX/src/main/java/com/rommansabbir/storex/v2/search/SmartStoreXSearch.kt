package com.rommansabbir.storex.v2.search

import android.content.Context
import java.lang.ref.WeakReference

internal interface SmartStoreXSearch {
    fun getAllKeys(context: WeakReference<Context>): MutableList<String>
}

