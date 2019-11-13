package pablo.molina.jsonform

import android.content.Context
import android.widget.*
import pablo.molina.jsonform.widgets.PmCheckBox
import pablo.molina.jsonform.widgets.PmRadioButton

/**
 * Created by Pablo Molina on 17-10-2019. s.pablo.molina@gmail.com
 */
open class JsonFormDrawerImpl(val context: Context){
    var currId = 1


    fun getLayoutId():Int{
        return currId++
    }

    /**
     * Obtiene el RadioGroup que almacenara los items de CheckBox
     * @param orientation   Orientacion de los elementos dentro del layout
     * @return LinearLayout configurado
     */
    fun drawerRadioGroup(orientation: String?, preferedOrientation: String?, weight: Float?): RadioGroup {
        return RadioGroup(context).apply {
            this.orientation   = Layout.getLayourOrientation(orientation)
            this.layoutParams  = Layout.GroupParams(weight, preferedOrientation)
            this.id = getLayoutId()
        }
    }

    /**
     * Obtiene el LinearLayout con padding para colocar despues de un ScrollView
     * @return LinearLayout configurado
     */
    fun drawerAfterScroll(): LinearLayout {
        return LinearLayout(context).apply {
            orientation     = LinearLayout.VERTICAL
            layoutParams    = Layout.p3
            setPadding(
                Layout.scrollPadding[0],
                Layout.scrollPadding[1],
                Layout.scrollPadding[2],
                Layout.scrollPadding[3]
            )
            this.id = getLayoutId()
        }
    }

    /**
     * Obtiene el LinearLayout que almacenara los items de CheckBox
     * @param orientation           Orientacion de los elementos dentro del layout
     * @param preferedOrientation   Orientacion preferida
     * @param weight                Distribucion del elemento en el grupo
     * @return LinearLayout configurado
     */
    fun drawerGroup(orientation: String?, preferedOrientation: String?, weight: Float?): LinearLayout {
        return LinearLayout(context).apply {
            this.orientation   = Layout.getLayourOrientation(orientation)
            this.layoutParams  = Layout.GroupParams(weight, preferedOrientation)
            this.id = getLayoutId()
        }
    }

    /**
     * Obtiene el ScrollView que almacenara subitems
     * @return ScrollView configurado
     */
    fun drawerScroll(): ScrollView {
        return ScrollView(context).apply {
            this.layoutParams  = Layout.p3
            this.id = getLayoutId()
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
            setPadding(
                Layout.boxPadding[0],
                Layout.boxPadding[1],
                Layout.boxPadding[2],
                Layout.boxPadding[3]
            )
            this.id = getLayoutId()
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
            this.id = getLayoutId()
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
            //setBackgroundResource(R.color.iconYellow) // Temporal
            this.id = getLayoutId()
            setPadding(
                Layout.spinnerPadding[0],
                Layout.spinnerPadding[1],
                Layout.spinnerPadding[2],
                Layout.spinnerPadding[3]
            )
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
            this.id = getLayoutId()
        }
    }


    /**
     * Obtiene el EdiText
     * @param description           Texto del Texview
     * @param style                 Estilo a aplicar al Texview Ejm.: R.style.mystyle
     * @param preferedOrientation   Orientacion preferida
     * @return EdiText configurado
     */
    fun drawerButton(): TextView {
        return Button(context).apply {
            layoutParams = Layout.p3
            this.id = getLayoutId()
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
            this.id = getLayoutId()
        }
    }

    /**
     * Obtiene el CheckBox
     * @param   parentId                Id del contenedor de los checkboxs
     * @param   id                      Id especifico del checkbox
     * @param   descripcion             Texto del checkbox
     * @param   weight                  Distribucion del elemento en el conjunto
     * @return CheckBox configurado
     */
    fun drawerCheckBox(parentId:Int, id:String, descripcion: String, weight: Float?): PmCheckBox {
        //AppCompatCheckBox(context)
        return PmCheckBox(context).apply {
            this.parentId           = parentId
            this.itemId             = id
            this.text               = descripcion
            this.layoutParams       = Layout.CheckBoxParams(weight)
            this.id                 = getLayoutId()
        }
    }

    /**
     * Obtiene un RadioButton configurado con parametros
     * @param   parentId                Id del radiogroup
     * @param   id                      Id del valor del radiobutton
     * @param   value                   Valor a mostrar del radio button
     * @param   weight                  Distribucion del elemento en el conjunto
     * @return RadioButton configurado
     */
    fun drawerRadioButton(parentId:Int, id:String, value: String, weight: Float?): PmRadioButton {
        return PmRadioButton(context).apply {
            this.parentId           = parentId
            this.itemId             = id
            text            = value
            //tag             = id
            layoutParams    = Layout.CheckBoxParams(weight)
            this.id         = getLayoutId()
        }
    }

    /**
     * Obtiene un TextView de tipo titulo
     * @param   id                      Id
     * @param   description                   Texto a mostrar
     * @return  TextView configurado
     */
    fun drawerTitle(id:Int, description: String): TextView {
        return TextView(context).apply {
            this.text   = description
            this.id     = getLayoutId()
            //setTextAppearance(context, R.style.labelTitle)
            layoutParams = Layout.p2
            setPadding(
                Layout.titlePadding[0],
                Layout.titlePadding[1],
                Layout.titlePadding[2],
                Layout.titlePadding[3])
        }
    }

    /**
     * Obtiene un Switch
     * @param   description         Texto a mostrar
     * @param   textOff             Texto a mostrar switch off
     * @param   textOn              Texto a mostrar switch on
     * @return  Switch configurado
     */
    fun drawerSwitch(description: String, textOff:String, textOn:String): Switch {
        return Switch(context).apply {
            this.id         = getLayoutId()
            this.text       = description
            this.textOff    = textOff
            this.textOn     = textOn
        }
    }
}
interface JsonFormDrawer{
    //fun drawerBoxInput(preferedOrientation: String?): LinearLayout
}