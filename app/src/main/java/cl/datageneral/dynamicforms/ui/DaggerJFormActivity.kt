package cl.datageneral.dynamicforms.ui

import android.util.Log
import android.view.View
import android.widget.*
import pablo.molina.jsonform.event.JFormAction
import pablo.molina.jsonform.event.JFormEvent
import dagger.android.support.DaggerAppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import pablo.molina.jsonform.JsonForm
import pablo.molina.jsonform.fragments.SelectDateFragment
import pablo.molina.jsonform.fragments.SelectTimeFragment
import pablo.molina.jsonform.spinner.SelectableItem
import pablo.molina.jsonform.widgets.PmCheckBox
import pablo.molina.jsonform.widgets.PmRadioButton

/**
 * Created by Pablo Molina on 06-11-2019. s.pablo.molina@gmail.com
 */
open class DaggerJFormActivity: DaggerAppCompatActivity(){

    var jsonForm:JsonForm? = null

    var layout:View? = null

    var selectedItems:HashMap<Int, ArrayList<String>> = HashMap()

    fun startEvent(event: JFormEvent){
        when(event.type){
            JFormEvent.Type.CHANGE -> startEventChange(event)
            JFormEvent.Type.CLICK -> startEventClick(event)
            JFormEvent.Type.LOAD -> {}
        }
    }

    private fun startEventClick(event: JFormEvent){
        event.view!!.setOnClickListener { view ->
            when(event.view){
                is EditText ->  loadAction<EditText>(event.view!!, event.actions)
                is Spinner ->  loadAction<Spinner>(event.view!!, event.actions)
                is Button ->  loadAction<Button>(event.view!!, event.actions)
            }
        }
    }


    private fun startEventChange(event: JFormEvent){
        if(event.view==null) {
            Log.e("startEventChange", "View no disponible")
            return
        }


        if(event.view!! is RadioGroup){
            val rg = event.view!! as  RadioGroup
            rg.setOnCheckedChangeListener { radioGroup, i ->
                val rbId    = radioGroup.checkedRadioButtonId
                val rb      = findViewById<PmRadioButton>(rbId)
                selectedItems[rb.parentId] = arrayListOf(rb.itemId)
            }
        }
        if(event.view!! is PmCheckBox){
            val rg = event.view!! as  PmCheckBox
            rg.setOnCheckedChangeListener{ checkBoxView, isChecked  ->
                var list = selectedItems[rg.parentId]
                if(list==null){
                    list = ArrayList()
                    list.add(rg.itemId)
                }else{
                    if(isChecked){
                        list.add(rg.itemId)
                    }else{
                        list.remove(rg.itemId)
                    }
                }
                when{
                    list.size > 0   -> selectedItems[rg.parentId] = list
                    else            -> selectedItems.remove(rg.parentId)
                }
            }
        }
    }



    fun getValues():JSONObject{
        val jObj = JSONObject()
        for(a in jsonForm!!.widgets){
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

    inline fun <reified T : View>loadAction(view: View, actions:MutableList<JFormAction>){

        for(action in actions){
            if(view is EditText){
                if(action.predefined=="select_date"){
                    val newFragment = SelectDateFragment(view)
                    newFragment.show(fragmentManager, "DatePicker")
                }else if(action.predefined=="select_time"){
                    val newFragment = SelectTimeFragment(view)
                    newFragment.show(fragmentManager, "TimePicker")
                }
            }
            if (view is Button){
                if(action.type==JFormAction.Type.DISABLE){
                    Log.e("Se deshabilita algo", "-"+action.containerId)
                    val wgt = jsonForm!!.widgets[action.containerId]
                    wgt!!.isEnabled = false

                    /*if(wgt!! is EditText){
                        //wgt.
                        //wgt.inputType = InputType.TYPE_NULL
                        wgt.isEnabled = false
                    }*/
                }
                if(action.type==JFormAction.Type.ENABLE){
                    Log.e("Se habilita algo", "-"+action.containerId)
                    val wgt = jsonForm!!.widgets[action.containerId]
                    wgt!!.isEnabled = true

                    /*if(wgt!! is EditText){
                        //wgt.
                        //wgt.inputType = InputType.TYPE_NULL
                        wgt.isEnabled = false
                    }*/
                }
            }
            // Otras acciones

        }
    }

    inline fun <reified T : View>testFunction(view: View):Int{
        //SelectDateFragment(view as T::class)

        when (T::class){
            EditText::class -> {
                Log.e("testFunction", "EditText")}
            Spinner::class  -> {
                Log.e("testFunction", "Spinner")}
            Button::class   -> {
                Log.e("testFunction", "Button")}
            else -> {
                Log.e("testFunction", "Nulo")}
        }

        return 1
    }


    fun getSelectedSpin(sp: Spinner): Int {
        var resp = 0
        var obj:SelectableItem? = null
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
        var obj:SelectableItem? = null
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