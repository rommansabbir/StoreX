package com.rommansabbir.storex.v2

import com.rommansabbir.storex.v2.config.StoreXSmartConfig
import com.rommansabbir.storex.v2.objects.StoreAbleString
import com.rommansabbir.storex.v2.search.SmartStoreXSearch
import com.rommansabbir.storex.v2.search.SmartStoreXSearchImpl
import com.rommansabbir.storex.v2.smartstorex.SmartStoreX
import com.rommansabbir.storex.v2.smartstorex.SmartStoreXImpl
import com.rommansabbir.storex.v2.strategy.StoreXCachingStrategy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class SmartStoreXSearchTest : BaseTestClass() {
    private lateinit var smartStoreX: SmartStoreX
    private lateinit var smartStoreXSearch: SmartStoreXSearch

    @Before
    fun setup() {
        smartStoreX = SmartStoreXImpl()
        smartStoreXSearch = SmartStoreXSearchImpl()
    }

    @Test
    fun `store files to cache and files dir and search for those files keys`() {
        val cacheFileConfig = StoreXSmartConfig(
            WeakReference(context),
            "test_file",
            StoreAbleString("Hello..."),
            StoreXCachingStrategy.CacheDir
        )
        val filesFileConfig = StoreXSmartConfig(
            WeakReference(context),
            "test_file_1",
            StoreAbleString("Hello..."),
            StoreXCachingStrategy.FilesDir
        )
        smartStoreX.write(cacheFileConfig)
        smartStoreX.write(filesFileConfig)
        val key = smartStoreXSearch.getAllKeys(WeakReference(context))
        assert(key.contains("test_file"))
        assert(key.contains("test_file_1"))
    }
}