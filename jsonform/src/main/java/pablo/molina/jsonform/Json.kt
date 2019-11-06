package pablo.molina.jsonform

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Pablo Molina C. on 11-01-2017.
 */

object Json {

    // object
    fun getObject(mainObject: JSONObject, key: String): JSONObject? {
        var value: JSONObject? = null
        try {
            if (mainObject.has(key))
                value = getObject(mainObject.get(key).toString())
            //else
            //     return "";
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return value
    }

    // text
    fun getText(mainObject: JSONObject?, key: String): String {
        var value = String()
        if(mainObject!=null) {
            try {
                if (mainObject.has(key)) {
                    value = mainObject.get(key).toString()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return value
    }

    // text
    fun getInt(mainObject: JSONObject?, key: String, default:Int): Int {
        var value = default
        if(mainObject!=null) {
            try {
                if (mainObject.has(key))
                    value = mainObject.getInt("key")
                //else
                //     return "";
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                //e.printStackTrace()
            }
        }

        return value
    }

    // text
    fun getBoolean(mainObject: JSONObject?, key: String): Boolean {
        var value = false
        if(mainObject!=null) {
            try {
                if (mainObject.has(key))
                    value = mainObject.getBoolean(key)
                //else
                //     return "";
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                //e.printStackTrace()
            }
        }
        return value
    }

    // toObject
    fun getObject(responseStr: String): JSONObject? {
        var mainObject: JSONObject? = null
        try {
            mainObject = JSONObject(responseStr)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return mainObject
    }

    // array
    fun getArray(mainObject: JSONObject?, key: String): JSONArray? {
        var r1: JSONArray? = null
        if(mainObject!=null) {
            try {
                if (mainObject.has(key)) {
                    r1 = mainObject.getJSONArray(key)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return r1
    }

    fun has(mainObject: JSONObject?, key: String):Boolean{
        var r = false
        if(mainObject!=null) {
            try {
                r = mainObject.has(key)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return r
    }
}
