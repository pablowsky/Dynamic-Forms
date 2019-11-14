package pablo.molina.jsonform

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import org.json.JSONArray
import org.json.JSONObject
import pablo.molina.jsonform.spinner.SelectableItem

/**
 * Created by Pablo Molina on 13-11-2019. s.pablo.molina@gmail.com
 */
class JsonFormSerializer(val jform: JsonForm) {
    var selectedItems:HashMap<Int, ArrayList<String>> = HashMap()


    fun getValues(): JSONObject {
        val widgets = jform.getWidgets()
        val jObj = JSONObject()
        for(a in widgets){
            val value:String? = when(a.value){
                is EditText -> {
                    val b = a.value as EditText
                    b.text.toString()
                }
                is Spinner -> {
                    getSelectedSpinString(a.value as Spinner)
                }
                else ->  null
            }
            if(value!=null) {
                jObj.put(a.key.toString(), value)
            }
        }
        for(item in selectedItems){
            jObj.put(item.key.toString(), JSONArray(item.value))
        }
        return jObj
    }

    fun addValue(id:Int, list: ArrayList<String>){
        selectedItems[id] = list
    }

    fun getValue(id:Int): java.util.ArrayList<String>? {
        return selectedItems[id]
    }

    fun rmValue(id:Int){
        selectedItems.remove(id)
    }

    fun getSelectedSpin(sp: Spinner): Int {
        var resp = 0
        var obj: SelectableItem? = null
        try {
            obj = sp.selectedItem as SelectableItem
        }catch (e:TypeCastException){
            Log.e("getSelectedSpin",e.toString())
        }
        if(obj!=null) {
            if (obj.value != null) {
                resp = obj.value.toString().toInt()
            }
        }
        return resp
    }

    fun getSelectedSpinString(sp: Spinner): String {
        var resp:String = ""
        var obj: SelectableItem? = null
        try {
            obj = sp.selectedItem as SelectableItem
        }catch (e:TypeCastException){
            Log.e("getSelectedSpin",e.toString())
        }
        if(obj!=null) {
            if (obj.value != null) {
                resp = obj.id.toString()
            }
        }
        return resp
    }
}