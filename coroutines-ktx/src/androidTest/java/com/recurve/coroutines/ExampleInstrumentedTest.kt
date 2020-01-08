package com.recurve.coroutines

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val mistake = 10L

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.recurve.coroutines.test", appContext.packageName)

        val now = System.currentTimeMillis()
        val delay = 1000L
        val result = io(delay = delay) {
            System.currentTimeMillis()
        }
        result{
            assertTrue((it - delay - now) <= mistake )
        }
    }


}
