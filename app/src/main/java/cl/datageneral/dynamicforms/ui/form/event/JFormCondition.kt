package cl.datageneral.dynamicforms.ui.form.event

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */
class JFormCondition{
    var type:Type?      = null
    var valor0:String   = String()
    var valor1:String   = String()
    var items:MutableList<Int>  = ArrayList()

    fun result():Boolean{
        return false
    }

    enum class Type{ET, NE, GT, LT}
}