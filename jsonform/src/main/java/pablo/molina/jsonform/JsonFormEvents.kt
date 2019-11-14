package pablo.molina.jsonform

import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import pablo.molina.jsonform.event.JFormAction
import pablo.molina.jsonform.event.JFormEvent
import pablo.molina.jsonform.fragments.SelectDateFragment
import pablo.molina.jsonform.fragments.SelectTimeFragment
import pablo.molina.jsonform.widgets.PmCheckBox
import pablo.molina.jsonform.widgets.PmRadioButton

/**
 * Created by Pablo Molina on 06-11-2019. s.pablo.molina@gmail.com
 */
open class JsonFormEvents(val jform: JsonForm, val activity: AppCompatActivity){

    fun startAllEvents(){
        if(jform.events.size > 0){
            for(event in jform.events){
                startEvent(event)
            }
        }
    }

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
            rg.setOnCheckedChangeListener { radioGroup, _ ->
                val rb      = activity.findViewById<PmRadioButton>(radioGroup.checkedRadioButtonId)
                jform.serializer.addValue(rb.parentId, arrayListOf(rb.itemId))
                //selectedItems[rb.parentId] = arrayListOf(rb.itemId)
            }
        }
        if(event.view!! is PmCheckBox){
            val rg = event.view!! as  PmCheckBox
            rg.setOnCheckedChangeListener{ checkBoxView, isChecked  ->
                //var list = selectedItems[rg.parentId]
                var list = jform.serializer.getValue(rg.parentId)
                if(list==null){
                    list = ArrayList()
                }
                if(isChecked){
                    list.add(rg.itemId)
                }else{
                    list.remove(rg.itemId)
                }
                when{
                    list.size > 0   -> jform.serializer.addValue(rg.parentId, list) //selectedItems[rg.parentId] = list
                    else            -> jform.serializer.rmValue(rg.parentId) //selectedItems.remove(rg.parentId)
                }
            }
        }
    }

    inline fun <reified T : View>loadAction(view: View, actions:MutableList<JFormAction>){

        for(action in actions){
            if(view is EditText){
                if(action.predefined=="select_date"){
                    val newFragment = SelectDateFragment(view)
                    newFragment.show(activity.fragmentManager, "DatePicker")
                }else if(action.predefined=="select_time"){
                    val newFragment = SelectTimeFragment(view)
                    newFragment.show(activity.fragmentManager, "TimePicker")
                }
            }
            if (view is Button){
                if(action.type==JFormAction.Type.DISABLE){
                    Log.e("Se deshabilita algo", "-"+action.containerId)
                    val wgt = jform.getWidgets()[action.containerId]
                    wgt!!.isEnabled = false

                    /*if(wgt!! is EditText){
                        //wgt.
                        //wgt.inputType = InputType.TYPE_NULL
                        wgt.isEnabled = false
                    }*/
                }
                if(action.type==JFormAction.Type.ENABLE){
                    Log.e("Se habilita algo", "-"+action.containerId)
                    val wgt = jform.getWidgets()[action.containerId]
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

}