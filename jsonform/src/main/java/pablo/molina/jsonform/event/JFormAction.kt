package pablo.molina.jsonform.event

import org.json.JSONArray

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */
class JFormAction{
    var containerId:Int     = 0
    var predefined:String   = String()
    var items: JSONArray?   = null
    var type: Type?         = null

    enum class Type{SHOW, HIDE, DISABLE, ENABLE, SET, UNSET, ADD_MANDANTORY, RM_MANDATORY, OPEN, STORE}
}