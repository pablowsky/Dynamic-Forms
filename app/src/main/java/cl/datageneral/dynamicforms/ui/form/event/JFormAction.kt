package cl.datageneral.dynamicforms.ui.form.event

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */
class JFormAction{
    val containerId:Int     = 0
    val predefined:String   = String()
    val type:Type?          = null

    enum class Type{SHOW, HIDE, DISABLE, ENABLE, SET, UNSET, ADD, ADD_MANDANTORY, RM_MANDATORY, OPEN}
}