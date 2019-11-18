package pablo.molina.jsonform

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import pablo.molina.jsonform.spinner.SelectableItem
import android.widget.*
import pablo.molina.jsonform.widgets.CheckBoxGroup


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
            val widget      = a.value
            val mandatory   = jform.mandatoryFields.contains(a.key)
            val value:String? = when(widget) {
                is EditText -> getEditTextValue(widget, mandatory)
                is Spinner -> getSpinnerValue(widget, mandatory)
                is Switch -> getSwitchValue(widget)
                else -> null
            }
            if(!value.isNullOrEmpty()) {
                jObj!!.put(a.key.toString(), value)
            }

            val value2:JSONArray? = when(widget){
                is RadioGroup       -> getRadioGroupValue(a.key, mandatory)
                is CheckBoxGroup    -> getCheckBoxGroupValue(a.key, mandatory)
                else ->  null
            }
            if(value2!=null) {
                jObj!!.put(a.key.toString(), value2)
            }
        }
    }

    private fun getEditTextValue(widget:EditText, mandatory:Boolean):String{
        val value = widget.text.toString()
        widget.error = if(value.isEmpty() && mandatory) {
            jform.styles.messageError
        }else{
            null
        }
        return value
    }
    private fun getSpinnerValue(widget:Spinner, mandatory:Boolean):String{
        val value = getSelectedSpinString(widget)
        widget.background = if(value.isEmpty() && mandatory) {
            jform.styles.spinnerError
        }else{
            jform.styles.spinnerNormal
        }
        return value
    }
    private fun getSwitchValue(widget:Switch):String{
        return widget.isChecked.toString()
    }
    private fun getRadioGroupValue(id:Int, mandatory:Boolean):JSONArray?{
        return when {
            selectedItems.contains(id) -> JSONArray(selectedItems[id])
            mandatory ->
                // genero aviso de error

                null
            else -> null
        }
    }
    private fun getCheckBoxGroupValue(id:Int, mandatory:Boolean):JSONArray?{
        return when {
            selectedItems.contains(id) -> JSONArray(selectedItems[id])
            mandatory ->
                // genero aviso de error

                null
            else -> null
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
        var resp = ""
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