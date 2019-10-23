package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.util.Log
import android.widget.*
import cl.datageneral.dynamicforms.R

/**
 * Created by Pablo Molina on 17-10-2019. s.pablo.molina@gmail.com
 */
open class JsonFormDrawerImpl(val context: Context){

    /**
     * Obtiene el RadioGroup que almacenara los items de CheckBox
     * @param orientation   Orientacion de los elementos dentro del layout
     * @return LinearLayout configurado
     */
    fun drawerRadioGroup(orientation: String?, weight: Float?): RadioGroup {
        /*val params:LinearLayout.LayoutParams = if(weight==null){
            Layout.p1
        }else{
            Layout.hWeightParam(weight)
        }*/
        return RadioGroup(context).apply {
            this.orientation   = Layout.getLayourOrientation(orientation)
            this.layoutParams  = Layout.GroupParams(weight)
            //Log.e("drawerGroup", weight.toString())
            //this.setBackgroundColor(resources.getColor(R.color.iconYellow))
        }
    }

    /**
     * Obtiene el LinearLayout que almacenara los items de CheckBox
     * @param orientation   Orientacion de los elementos dentro del layout
     * @return LinearLayout configurado
     */
    fun drawerGroup(orientation: String?, weight: Float?): LinearLayout {
        /*val params:LinearLayout.LayoutParams = if(weight==null){
            Layout.p1
        }else{
            Layout.hWeightParam(weight)
        }*/
        return LinearLayout(context).apply {
            this.orientation   = Layout.getLayourOrientation(orientation)
            this.layoutParams  = Layout.GroupParams(weight)
            //Log.e("drawerGroup", weight.toString())
            //this.setBackgroundColor(resources.getColor(R.color.iconYellow))
        }
    }

    /**
     * Obtiene el ScrollView que almacenara subitems
     * @return ScrollView configurado
     */
    fun drawerScroll(): ScrollView {
        return ScrollView(context).apply {
            this.layoutParams  = Layout.p3
        }
    }

    /**
     * Obtiene el LinearLayout que almacenara el label y el input
     * @param preferedOrientation   Orientacion preferida
     * @return LinearLayout configurado
     */
    fun drawerBoxInput(preferedOrientation: String?): LinearLayout {
        return LinearLayout(context).apply {
            this.orientation   = Layout.getLayourOrientation(preferedOrientation)
            this.layoutParams  = Layout.BoxParams()
            //this.setBackgroundColor(resources.getColor(R.color.iconYellow))
        }
    }

    /**
     * Obtiene el TextView que almacenara el label del grupo
     * @param description           Texto del Texview
     * @param style                 Estilo a aplicar al Texview Ejm.: R.style.mystyle
     * @param preferedOrientation   Orientacion preferida
     * @return TextView configurado
     */
    fun drawerLabel(description:String, style:Int, preferedOrientation: String?, weight: Float=1.0f): TextView {
        return TextView(context).apply {
            text = description
            setTextAppearance(context, style)
            layoutParams = Layout.LabelParams(preferedOrientation, context, weight)
        }
    }

    /**
     * Obtiene el Spinner configurado
     * @param description           Texto del Texview
     * @param style                 Estilo a aplicar al Texview Ejm.: R.style.mystyle
     * @param preferedOrientation   Orientacion preferida
     * @return TextView configurado
     */
    fun drawerListbox(preferedOrientation: String?, weight: Float=2.0f): Spinner {
        return Spinner(context).apply {
            setBackgroundResource(R.color.iconYellow) // Temporal
            /*setPadding(
                spinnerPadding[0],
                spinnerPadding[1],
                spinnerPadding[2],
                spinnerPadding[3]
            )*/
            layoutParams = Layout.EditTextParams(preferedOrientation, context, weight)
        }
    }

    /**
     * Obtiene el EdiText
     * @param description           Texto del Texview
     * @param style                 Estilo a aplicar al Texview Ejm.: R.style.mystyle
     * @param preferedOrientation   Orientacion preferida
     * @return EdiText configurado
     */
    fun drawerEditText(lines: Int = 1, style:Int, preferedOrientation: String?, weight: Float=2.0f): TextView {
        return EditText(context).apply {
            setTextAppearance(context, style)
            layoutParams = Layout.EditTextParams(preferedOrientation, context, weight)
            setLines(lines)
        }
    }

    /**
     * Obtiene el ImageView
     * @param   src                     Imagen a mostrar
     * @param   preferedOrientation     Orientacion preferida
     * @param   weight                  Distribucion del elemento en el conjunto
     * @return EdiText configurado
     */
    fun drawerImage(src:Int, preferedOrientation: String?, weight: Float=2.0f): ImageButton {
        return ImageButton(context).apply {
            layoutParams    = Layout.EditTextParams(preferedOrientation, context, weight)
            cropToPadding   = true
            setBackgroundColor(resources.getColor(android.R.color.transparent))
            setImageResource(src)
        }
    }

    /**
     * Obtiene el CheckBox
     * @param   src                     Imagen a mostrar
     * @param   preferedOrientation     Orientacion preferida
     * @param   weight                  Distribucion del elemento en el conjunto
     * @return EdiText configurado
     */
    fun drawerCheckBox(id:Int, value: String, weight: Float?): CheckBox {
        return CheckBox(context).apply {
            text            = value
            tag             = id
            layoutParams    = Layout.CheckBoxParams(weight)
        }
    }

    /**
     * Obtiene un RadioButton configurado con parametros
     * @param   id                      Id del valor del radiobutton
     * @param   value                   Valor a mostrar del radio button
     * @param   weight                  Distribucion del elemento en el conjunto
     * @return RadioButton configurado
     */
    fun drawerRadioButton(id:Int, value: String, weight: Float?): RadioButton {
        return RadioButton(context).apply {
            text            = value
            tag             = id
            layoutParams    = Layout.CheckBoxParams(weight)
        }
    }
}
interface JsonFormDrawer{
    //fun drawerBoxInput(preferedOrientation: String?): LinearLayout
}