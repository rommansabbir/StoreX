package com.rommansabbir.storexdemo

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.rommansabbir.storex.StoreX
import com.rommansabbir.storex.Subscriber
import com.rommansabbir.storex.callbacks.EventCallback


class MainActivity : AppCompatActivity(), EventCallback {

    private fun askAndroid10Perm() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            121
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askAndroid10Perm()
    }

    override fun onDataChanges(subscriber: Subscriber, instance: StoreX) {

    }
}