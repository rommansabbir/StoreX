package com.rommansabbir.storexdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rommansabbir.storex.*
import com.rommansabbir.storex.callbacks.EventCallback
import com.rommansabbir.storex.callbacks.GetCallback
import com.rommansabbir.storex.callbacks.SaveCallback

class MainActivity : AppCompatActivity(), EventCallback {

    private var OBSERVER_ID_1 = 1
    private var OBSERVER_ID_2 = 2
    private var OBSERVER_ID_3 = 3
    private var OBSERVER_ID_4 = 4

    private var key = "key"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        findViewById<Button>(R.id.am_cache_btn).setOnClickListener {
            val model = UserData().apply {
                username = "Cached on Main Thread: Signature :${this.objectId}"
            }
            StoreXCore.instance().put(key, model).let {
                if (it) {
                    findViewById<TextView>(R.id.am_tv_cache_main_thread).apply {
                        text = "Cached On Main Thread : ${model.username}"
                    }
                }
            }
        }

        findViewById<Button>(R.id.am_cache_asnyc_btn).setOnClickListener {
            StoreXCore.instance().put(key, UserData().apply {
                username = "Cached Asyc: Signature :${this.objectId}"
            }, object : SaveCallback<UserData> {
                override fun onDone(value: UserData, exception: Exception?) {
                    findViewById<TextView>(R.id.am_tv_do_cache_async).apply {
                        text =
                            "Cached Async : ${if (exception == null) value.username else exception.message}"
                    }
                }
            })
        }

        findViewById<Button>(R.id.am_get_cache_btn).setOnClickListener {
            storeXInstance().get(key, UserData::class.java).let {
                findViewById<TextView>(R.id.am_tv_get_cache_main).apply {
                    text = "Get On Main Thread : ${it.username}"
                }
            }
        }

        findViewById<Button>(R.id.am_get_cache_async_btn).setOnClickListener {
            storeXInstance().get(key, UserData::class.java, object : GetCallback<UserData>{
                override fun onSuccess(value: UserData?, exception: Exception?) {
                    findViewById<TextView>(R.id.am_tv_get_cache_async).apply {
                        text =
                            "Get Async : ${if (exception == null) value!!.username else exception.message}"
                    }
                }
            })
        }

        val list = arrayListOf(subscriber1)

        findViewById<Button>(R.id.am_btn_add_observers).setOnClickListener {

            StoreXCore.instance().addSubscriber(list)
            findViewById<TextView>(R.id.am_tv_observers_update).text = "Subscriber Added :: Size :${list.size}"
        }

        findViewById<Button>(R.id.am_remove_observer_btn).setOnClickListener{
            storeXInstance().removeSubscriber(list)
            findViewById<TextView>(R.id.am_tv_observer_remove).apply {
                text = "Removed"
            }
            findViewById<TextView>(R.id.am_tv_observers_update).text = ""
        }
        storeXInstance().removeSubscriber(subscriber1)
    }

    private val callback1 = object : EventCallback{
        override fun onDataChanges(subscriber: Subscriber, instance: StoreX) {
            // Callback return an instance of StoreX and the specific subscriber
        }
    }


    //Create a new subscriber by providing the `Key`, `Observer ID (Must be unique)` and the `Callback`
    private var subscriber1 = Subscriber(key, OBSERVER_ID_1, callback1)
    private var subscriber2 = Subscriber(key, OBSERVER_ID_2, this)
    private var subscriber3 = Subscriber(key, OBSERVER_ID_3, this)

    override fun onDataChanges(subscriber: Subscriber, instance: StoreX) {
        instance.get(subscriber.key, UserData::class.java, object : GetCallback<UserData> {
            override fun onSuccess(value: UserData?, exception: Exception?) {
                var temp = findViewById<TextView>(R.id.am_tv_observers_update).text.toString()
                if (exception == null) {
                    temp += "\nSubscriber ID:  ${subscriber.subscriberID} :: ${value!!.username} :: ${value.objectId} :: ${System.currentTimeMillis()}"
                    findViewById<TextView>(R.id.am_tv_observers_update).apply {
                        text = temp
                    }
                } else {
                    temp += "\nSubscriber ID:  ${subscriber.subscriberID} :: ${exception.message} :: ${System.currentTimeMillis()}"
                    findViewById<TextView>(R.id.am_tv_observers_update).apply {
                        text = temp
                    }
                }
            }
        })
    }
}