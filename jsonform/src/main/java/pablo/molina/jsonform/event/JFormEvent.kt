package pablo.molina.jsonform.event

import android.view.View

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */
class JFormEvent{
    var view:View?  = null
    var type: Type?  = null
    var condition: JFormCondition?   = null
    var actions:MutableList<JFormAction>  = ArrayList()


    enum class Type{CHANGE, CLICK, LOAD}
}