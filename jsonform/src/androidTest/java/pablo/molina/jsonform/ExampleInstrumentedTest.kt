package pablo.molina.jsonform

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

    val textJson = "{\"descripcion\":\"some text\", \"age\":4}"

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("pablo.molina.jsonform.test", appContext.packageName)
    }

    @Test
    fun addition_isCorrect() {
        val obj = Json.getObject(textJson)

        val age = Json.getInt(obj, "age", 0)

        assertEquals(4, age)
    }
}
