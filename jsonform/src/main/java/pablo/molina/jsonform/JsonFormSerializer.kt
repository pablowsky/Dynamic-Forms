package pablo.molina.jsonform

import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.view.get
import org.json.JSONArray
import org.json.JSONObject
import pablo.molina.jsonform.spinner.SelectableItem
import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatRadioButton


/**
 * Created by Pablo Molina on 13-11-2019. s.pablo.molina@gmail.com
 */
class JsonFormSerializer(val jform: JsonForm) {
    var selectedItems:HashMap<Int, ArrayList<String>> = HashMap()
    var jObj:JSONObject? = null

    fun checkForm(){
        val widgets = jform.getWidgets()
        jObj = JSONObject()
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
            if(!value.isNullOrEmpty()) {
                jObj!!.put(a.key.toString(), value)
            }else{
                if(jform.mandatoryFields.contains(a.key)){
                    when(a.value){
                        is EditText -> {
                            val b = a.value as EditText
                            b.setError("Falta completar")
                        }
                        is Spinner -> {
                            val b = a.value as Spinner
                            b.background = jform.styles.spinnerError
                            //b.set
                        }
                        is RadioGroup -> {
                            Log.e("RadioGroup","error")
                            val radiogroup = a.value as RadioGroup
                            for(rb in 0..radiogroup.childCount){
                                val myrb = radiogroup.getChildAt(rb) as AppCompatRadioButton
                                //val myrb = radiogroup[rb] as RadioButton
                            }
                            //b.setEr
                        }
                        //else ->  null
                    }
                }

            }
        }
        for(item in selectedItems){
            jObj!!.put(item.key.toString(), JSONArray(item.value))
        }
    }

    fun getValues(): JSONObject {
        return jObj!!
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
                resp = if(obj.id!=null) {
                    obj.id.toString()
                }else{
                    ""
                }
            }
        }
        return resp
    }
}