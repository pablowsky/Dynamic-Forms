package cl.datageneral.dynamicforms.ui.form.event

/**
 * Created by Pablo Molina on 28-10-2019. s.pablo.molina@gmail.com
 */
class JFormCondition{
    val valor0:String   = String()
    val valor1:String   = String()

    fun result():Boolean{
        return false
    }

    enum class Type{ET, NE, GT, LT}
}