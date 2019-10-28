package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.view.View
import cl.datageneral.dynamicforms.ui.form.event.JFormEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */

class JsonEvent(val objContainer:JSONObject){

    fun proc(){
        val evtObj      = Json.getObject(objContainer, "event")
        val event       = JFormEvent()
        event.type      = getType(evtObj)

        val actions      = Json.getArray(evtObj, "actions")
        if(actions!=null){
            val size = actions.length()
            for (a in 0 until size){
                val aobj    = actions.getJSONObject(a)
                val action  = Json.getObject(aobj, "action")

            }
        }
    }

    private fun getType(obj:JSONObject?):JFormEvent.Type?{
        return when(Json.getText(obj, "type")){
            "change"    ->    JFormEvent.Type.CHANGE
            "click"     ->    JFormEvent.Type.CLICK
            "load"      ->    JFormEvent.Type.LOAD
            else -> null
        }
    }


}