package pablo.molina.jsonform

import android.util.Log
import android.view.View
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject
import pablo.molina.jsonform.spinner.LoadSpin
import pablo.molina.jsonform.widgets.CheckBoxGroup
import pablo.molina.jsonform.widgets.PmCheckBox
import pablo.molina.jsonform.widgets.PmRadioButton

/**
 * Created by Pablo Molina on 15-11-2019. s.pablo.molina@gmail.com
 */
class JsonFormInflate(val jform: JsonForm) {

    fun init(data:JSONObject){
        for(key in data.keys()){
            //val item = data[key]
            //Log.e(key.toString(), item.javaClass.simpleName)
            val keyI = key.toInt()
            if(jform.getWidgets().containsKey(keyI)){
                val widget = jform.getWidgets()[keyI]

                //Log.e(key.toString(), widget!!.javaClass.simpleName)
                if(widget!=null) {
                    fill(widget, data[key])
                    /*if(item is String) {
                        filler(widget, item)
                    }else if(item is JSONArray) {
                        filler(widget, item)
                    }*/
                }
            }
        }
    }

    fun fill(widget:View, values: Any){
        if(widget is EditText || widget is Spinner || widget is Switch){
            val value = if(values is JSONArray){
                values[0].toString()
            }else{
                values as String
            }
            //val sValue = values as String
            filler(widget, value)
        }else if(widget is CheckBoxGroup || widget is RadioGroup){
            filler(widget, values as JSONArray)
        }
    }

    private fun filler(widget:View, value: String){
        when(widget){
            is EditText -> {
                widget.setText(value)
            }
            is Spinner -> {
                val valueI = value.toInt()
                LoadSpin.setSelected(widget, valueI)
            }
            is Switch -> {
                widget.isChecked = (value=="true")
            }
        }
    }

    private fun filler(widget:View, values: JSONArray){
        when(widget){
            is RadioGroup -> {

                val size = values.length()
                for (a in 0 until size){
                    val cValue = values.get(a)
                    for(rbKey in 0..widget.childCount){
                        val rb = widget.getChildAt(rbKey)
                        if(rb is PmRadioButton) {
                            if(rb.itemId==cValue.toString()){
                                rb.isChecked = true
                            }
                        }
                    }
                }

            }
            is CheckBoxGroup -> {
                //Log.e("key4","-"+values)
                // Verificar si es CheckBox
                val size = values.length()
                for (a in 0 until size){
                    val cValue = values.get(a)
                    for(rbKey in 0..widget.childCount){
                        val rb = widget.getChildAt(rbKey)
                        if(rb is PmCheckBox) {
                            //Log.e("key5",rb.itemId+"-"+cValue.toString())
                            if(rb.itemId==cValue.toString()){
                                rb.isChecked = true
                            }
                        }
                    }
                }

            }
        }
    }

    /*inline fun <reified T : View>testFunction(widget:LinearLayout, values: JSONArray):Int{
        val size = values.length()
        for (a in 0 until size){
            val cValue = values.get(a)
            for(rbKey in 0..widget.childCount){
                val rb = widget.getChildAt(rbKey)
                if(rb is T) {
                    //rb.
                    Log.e("key5",rb.itemId+"-"+cValue.toString())
                    if(rb.itemId==cValue.toString()){
                        rb.isChecked = true
                    }
                }
            }
        }

        when (T::class){
            EditText::class -> {Log.e("testFunction", "EditText")}
            Spinner::class  -> {Log.e("testFunction", "Spinner")}
            Button::class   -> {Log.e("testFunction", "Button")}
            else -> {Log.e("testFunction", "Nulo")}
        }

        return 1
    }*/


}