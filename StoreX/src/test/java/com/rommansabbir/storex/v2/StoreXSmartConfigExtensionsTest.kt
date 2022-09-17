package com.rommansabbir.storex.v2

import com.rommansabbir.storex.v2.extensions.getCacheDir
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class StoreXSmartConfigExtensionsTest : BaseTestClass() {

    @Test
    fun `null context should throw exception`() {
        var exception: Exception? = null
        try {
            ConfigUtils.getTestCacheDirConfig(WeakReference(null)).getCacheDir()
        } catch (e: Exception) {
            exception = e
        }
        assert(exception != null)
    }

    @Test
    fun `get cache dir path from test config`() {
        val config = ConfigUtils.getTestCacheDirConfig(WeakReference(context)).getCacheDir()
        assert(config.isNotEmpty())
    }
}