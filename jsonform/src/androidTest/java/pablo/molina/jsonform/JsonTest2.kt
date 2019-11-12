package pablo.molina.jsonform

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class JsonTest2 {

    val textJson = "{\"descripcion\":\"some text\", \"age\":4}"

 /*   @Before
    fun setup(){

    }*/

    @Test
    fun addition_isCorrect() {
        val obj = Json.getObject(textJson)

        val age = Json.getInt(obj, "age", 0)

        assertEquals(4, age)
    }
}
