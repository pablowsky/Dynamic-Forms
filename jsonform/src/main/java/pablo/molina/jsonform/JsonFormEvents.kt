package pablo.molina.jsonform

import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import org.json.JSONArray
import pablo.molina.jsonform.event.JFormAction
import pablo.molina.jsonform.event.JFormEvent
import pablo.molina.jsonform.fragments.SelectDateFragment
import pablo.molina.jsonform.fragments.SelectTimeFragment
import pablo.molina.jsonform.widgets.CheckBoxGroup
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
            JFormEvent.Type.CHANGE  -> startEventChange(event)
            JFormEvent.Type.CLICK   -> startEventClick(event)
            JFormEvent.Type.LOAD    -> startEventLoad(event)
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
            }
        }
        if(event.view!! is PmCheckBox){
            val rg = event.view!! as  PmCheckBox
            rg.setOnCheckedChangeListener{ checkBoxView, isChecked  ->
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
                    list.size > 0   -> jform.serializer.addValue(rg.parentId, list)
                    else            -> jform.serializer.rmValue(rg.parentId)
                }
            }
        }
    }

    private fun startEventLoad(event: JFormEvent){
        for(action in event.actions){
            loadActionFor(action)
        }
    }

    fun loadActionFor(action:JFormAction){
        val actionType  =   action.type!!
        val containerId =   action.containerId
        val items       =   action.items
        val widget      =   jform.getWidgets()[containerId]
        val widgetG     =   jform.widgetsGroup[containerId]

        when(actionType){
            JFormAction.Type.DISABLE    -> disableWidget(widget!!, false)
            JFormAction.Type.ENABLE     -> disableWidget(widget!!, true)
            JFormAction.Type.SHOW       -> showWidget(widgetG!!, true)
            JFormAction.Type.HIDE       -> showWidget(widgetG!!, false)
            JFormAction.Type.SET        -> setValue(widget!!, items!!)
            JFormAction.Type.UNSET      -> unsetValues(widget!!)
            JFormAction.Type.ADD_MANDANTORY -> addMandatory(containerId)
            JFormAction.Type.RM_MANDATORY   -> rmMandatory(containerId)
        }
    }

    fun addMandatory(containerId:Int){
        if(!jform.mandatoryFields.contains(containerId)){
            jform.mandatoryFields.add(containerId)
        }
    }

    fun rmMandatory(containerId:Int){
        if(jform.mandatoryFields.contains(containerId)){
            jform.mandatoryFields.remove(containerId)
        }
    }

    fun disableWidget(widget:View, enable:Boolean){
        when(widget){
            is EditText     -> widget.isEnabled = enable
            is Spinner      -> widget.isEnabled = enable
            is Switch       -> widget.isEnabled = enable
            is CheckBoxGroup -> {
                for(witem in widget.children){
                    witem.isEnabled = enable
                }
            }
            is RadioGroup -> {
                for(witem in widget.children){
                    witem.isEnabled = enable
                }
            }
        }
    }

    fun showWidget(widget:View, show:Boolean){
        widget.visibility = if(show) View.VISIBLE else View.GONE
    }

    fun setValue(widget:View, items:JSONArray){
        val inflater = JsonFormInflate(jform)
        inflater.fill(widget, items)
    }

    fun unsetValues(widget:View){
        val inflater = JsonFormInflate(jform)
        when(widget){
            is EditText         -> inflater.fill(widget,"")
            is Spinner          -> inflater.fill(widget,"0")
            is Switch           -> inflater.fill(widget,"false")
            is CheckBoxGroup    -> {
                for(witem  in widget.children){
                    witem as PmCheckBox
                    witem.isChecked = false
                }
            }
            is RadioGroup       -> {
                for(witem in widget.children){
                    witem as PmRadioButton
                    witem.isChecked = false
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
                loadActionFor(action)
                /*
                if(action.type==JFormAction.Type.DISABLE){
                    Log.e("Se deshabilita algo", "-"+action.containerId)
                    val wgt = jform.getWidgets()[action.containerId]
                    wgt!!.isEnabled = false

                }
                if(action.type==JFormAction.Type.ENABLE){
                    Log.e("Se habilita algo", "-"+action.containerId)
                    val wgt = jform.getWidgets()[action.containerId]
                    wgt!!.isEnabled = true

                }*/
            }
            // Otras acciones

        }
    }

}