package cl.datageneral.dynamicforms.ui.form

import pablo.molina.jsonform.event.JFormEvent
import org.json.JSONObject

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */

class JsonEvent(val objContainer:JSONObject){

    fun proc(){
        val evtObj      = pablo.molina.jsonform.Json.getObject(objContainer, "event")
        val event       = JFormEvent()
        event.type      = getType(evtObj)

        val actions      = pablo.molina.jsonform.Json.getArray(evtObj, "actions")
        if(actions!=null){
            val size = actions.length()
            for (a in 0 until size){
                val aobj    = actions.getJSONObject(a)
                val action  = pablo.molina.jsonform.Json.getObject(aobj, "action")

            }
        }
    }

    private fun getType(obj:JSONObject?): JFormEvent.Type?{
        return when(pablo.molina.jsonform.Json.getText(obj, "type")){
            "change"    ->    JFormEvent.Type.CHANGE
            "click"     ->    JFormEvent.Type.CLICK
            "load"      ->    JFormEvent.Type.LOAD
            else -> null
        }
    }


}