package com.rommansabbir.storexdemo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rommansabbir.storex.*
import com.rommansabbir.storex.callbacks.EventCallback
import com.rommansabbir.storex.callbacks.GetCallback
import com.rommansabbir.storex.callbacks.SaveCallback
import com.rommansabbir.storex.v2.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), EventCallback {

    private var OBSERVER_ID_1 = 1
    private var OBSERVER_ID_2 = 2
    private var OBSERVER_ID_3 = 3
    private var OBSERVER_ID_4 = 4

    private var key = "key"

    private lateinit var objectWriter: ObjectWriter

    private lateinit var testConfig: StoreXSmartConfig<StoreAbleObject>

    fun getTestCacheDirConfig(context: WeakReference<Context>) =
        StoreXSmartConfig(context, "testfile.file", StoreAbleObject(), StoreXCachingStrategy.CacheDir())

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        objectWriter = ObjectWriterImpl()
        testConfig = getTestCacheDirConfig(WeakReference(this))
        objectWriter.writeObject(
            testConfig.getCacheDir(),
            testConfig.fileName,
            testConfig.xObject.toJson()
        )
        val writtenObject =
            objectWriter.getWrittenObject(testConfig.getCacheDir(), testConfig.fileName)
        val storedObject = writtenObject.toStoreAbleObject(StoreAbleObject::class.java)
        val result =
            objectWriter.deleteWrittenObject(testConfig.getCacheDir(), testConfig.fileName)
        println()

        //
/*        findViewById<Button>(R.id.am_cache_btn).setOnClickListener {
            val model = UserData().apply {
                username = "Cached on Main Thread: Signature :${this.objectId}"
            }
            StoreXCore.instance(StoreXIdentifiers.anotherConfig).put(key, model).let {
                if (it) {
                    findViewById<TextView>(R.id.am_tv_cache_main_thread).apply {
                        text = "Cached On Main Thread : ${model.username}"
                    }
                }
            }
        }

        findViewById<Button>(R.id.am_cache_asnyc_btn).setOnClickListener {
            object : SaveCallback<UserData> {
                override fun onDone(value: UserData, exception: Exception?) {
                    findViewById<TextView>(R.id.am_tv_do_cache_async).apply {
                        text =
                            "Cached Async : ${if (exception == null) value.username else exception.message}"
                    }
                }
            }
            StoreXCore.instance(StoreXIdentifiers.anotherConfig)
            StoreXCore.instance(config).putS(key, UserData().apply {
                username = "Cached Asyc: Signature :${this.objectId}"
            })
        }

        findViewById<Button>(R.id.am_get_cache_btn).setOnClickListener {
            storeXInstance(StoreXIdentifiers.anotherConfig).get(key, UserData::class.java).let {
                findViewById<TextView>(R.id.am_tv_get_cache_main).apply {
                    text = "Get On Main Thread : ${it.username}"
                }
            }
        }

        findViewById<Button>(R.id.am_get_cache_async_btn).setOnClickListener {
            storeXInstance(StoreXIdentifiers.anotherConfig).get(
                key,
                UserData::class.java,
                object : GetCallback<UserData> {
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

            StoreXCore.instance(StoreXIdentifiers.anotherConfig).addSubscriber(list)
            findViewById<TextView>(R.id.am_tv_observers_update).text =
                "Subscriber Added :: Size :${list.size}"
        }

        findViewById<Button>(R.id.am_remove_observer_btn).setOnClickListener {
            storeXInstance(StoreXIdentifiers.anotherConfig).removeSubscriber(list)
            findViewById<TextView>(R.id.am_tv_observer_remove).apply {
                text = "Removed"
            }
            findViewById<TextView>(R.id.am_tv_observers_update).text = ""
        }*/
//        storeXInstance().removeSubscriber(subscriber1)
    }

    override fun onDataChanges(subscriber: Subscriber, instance: StoreX) {

    }

    /* private val callback1 = object : EventCallback {
         override fun onDataChanges(subscriber: Subscriber, instance: StoreX) {
             // Callback return an instance of StoreX and the specific subscriber
             Log.d("StoreXDemo", "Callback Invoked")
             if (subscriber.key == key) {
                 instance.get(key, UserData::class.java, object : GetCallback<UserData> {
                     @SuppressLint("SetTextI18n")
                     override fun onSuccess(value: UserData?, exception: Exception?) {
                         exception?.let {
                             findViewById<TextView>(R.id.am_tv_observer_remove).apply {
                                 text = exception.message.toString()
                                 Log.e("StoreXDemo", exception.message.toString())
                             }
                         } ?: run {
                             value?.let {
                                 findViewById<TextView>(R.id.am_tv_observer_remove).apply {
                                     text = "On Data Found From Cache: ${it.username}"
                                     Log.d("StoreXDemo", "On Data Found From Cache: ${it.username}")
                                 }
                             } ?: kotlin.run {
                                 findViewById<TextView>(R.id.am_tv_observer_remove).apply {
                                     text = "On Data Found From Cache: null"
                                     Log.e("StoreXDemo", "On Data Found From Cache: null")
                                 }
                             }
                         }
                     }
                 })
             }
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
     }*/
}