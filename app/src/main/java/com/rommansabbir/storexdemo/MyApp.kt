package com.rommansabbir.storexdemo

import android.app.Application
import com.rommansabbir.storex.StoreXCore

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        StoreXCore.init(this, getString(R.string.app_name))
    }
}