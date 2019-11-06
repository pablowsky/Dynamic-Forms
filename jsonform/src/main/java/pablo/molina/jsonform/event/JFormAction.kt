package pablo.molina.jsonform.event

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */
class JFormAction{
    var containerId:Int     = 0
    var predefined:String   = String()
    var type: Type?          = null

    enum class Type{SHOW, HIDE, DISABLE, ENABLE, SET, UNSET, ADD, ADD_MANDANTORY, RM_MANDATORY, OPEN}
}