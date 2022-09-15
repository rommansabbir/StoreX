package com.rommansabbir.storex.v2

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowApplication

abstract class BaseTestClass {
    internal lateinit var context: Application
    internal lateinit var shallowApp: ShadowApplication

    @Before
    fun setupContextApp() {
        context = ApplicationProvider.getApplicationContext()
        shallowApp = Shadows.shadowOf(context)
    }
}