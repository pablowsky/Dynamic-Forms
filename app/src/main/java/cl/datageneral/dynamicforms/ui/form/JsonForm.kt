package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.view.View
import android.widget.*
import cl.datageneral.dynamicforms.R
import pablo.molina.jsonform.event.JFormAction
import pablo.molina.jsonform.event.JFormCondition
import pablo.molina.jsonform.event.JFormEvent
import pablo.molina.jsonform.spinner.LoadSpin
import pablo.molina.jsonform.spinner.SelectableItem
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Pablo Molina on 15-10-2019. s.pablo.molina@gmail.com
 */
class JsonForm(val jsonArray: JSONArray, val mainLayout:View, context: Context): JsonFormDrawerImpl(context){

    private var baseLayout = LinearLayout(context)

    private val contBoxes       = arrayOf("linearlayout","afterscroll","scrollform","cardbox")
    private val contWidgets     = arrayOf("textedit","dateedit","timeedit","listbox","checkbox","radiobutton","button","title")
    private val contSpecials    = arrayOf("tablayout","tabitem")
    private val contPredefined  = arrayOf("add_items","check_items","pictures_gallery")

    val mandatoryFields:ArrayList<View>? = ArrayList()

    val events:ArrayList<JFormEvent> = ArrayList()

    fun getFields():View{
        baseLayout = LinearLayout(context)
        //var mv = View(context)
        val size = jsonArray.length()
        for (a in 0 until size){
            val obj         = jsonArray.getJSONObject(a)
            val container   = pablo.molina.jsonform.Json.getObject(obj,"container")

            extractObj(container!!, mainLayout)
        }
        return mainLayout
    }

/*    fun getMainForm():View{
        var mv = View(context)
        val size = jsonArray.length()
        for (a in 0 until size){
            val obj         = jsonArray.getJSONObject(a)
            val container   = Json.getObject(obj,"container")
            val usage       = Json.getText(container!!,"usage")

            if(usage == "main" ){
                mv = extractObj(obj, mainLayout)
            }
        }
        return mv
    }*/

    fun extractObj(obj:JSONObject, parentView:View?):View?{
        val type        = pablo.molina.jsonform.Json.getText(obj,"type")
        val container   = pablo.molina.jsonform.Json.getObject(obj, "container")
        val childs      = pablo.molina.jsonform.Json.getArray(obj, "_childs")

        val c:View? = when(getType(type)){
            ContainerType.BOX ->{
                var linear = someProc("afterscroll", obj)
                if(childs!=null){
                    val size = childs.length()
                    for (a in 0 until size){
                        val sobj    = childs.getJSONObject(a)
                        val cChild  = pablo.molina.jsonform.Json.getObject(sobj, "container")
                        linear      = extractObj(cChild!!, linear)
                    }
                }
                val scroll = someProc(type, obj)
                add(scroll, linear)
            }
            ContainerType.WIDGET -> {
                procWidget(type, obj)
            }
            /*ContainerType.SPECIAL ->
            ContainerType.PREDEFINED ->*/
            else -> {
                null
            }
        }
        return if(c==null){
            parentView
        }else{
            add(parentView, c)
        }
    }

    private fun someProc(type:String, obj: JSONObject?):View?{

        return when(type){
            "boxInput"  -> {
                drawerBoxInput(null)
            }
            "afterscroll"  -> {
                drawerAfterScroll()
            }
            "scrollform"  -> {
                drawerScroll()
            }
            "tabitem"       -> {
                TabItem(context)
            }
            "tablayout"     -> {
                TabLayout(context)
            }
            "add_items"     -> View(context)
            "check_items"   -> View(context)
            "pictures_gallery" -> View(context)
            "button"        -> {
                Button(context)
            }
            else -> View(context)
        }
    }

    private fun procWidget(type:String, obj: JSONObject?):View?{
        val description     = pablo.molina.jsonform.Json.getText(obj, "description")
        val id              = pablo.molina.jsonform.Json.getInt(obj, "id", 0)
        val mandatory       = pablo.molina.jsonform.Json.getBoolean(obj, "mandatory")
        val evt             = getEvent(obj)

        val widget = when(type){
            "title"       -> {
                drawerTitle(id, description)
            }
            "textedit"       -> {
                val lines           = pablo.molina.jsonform.Json.getInt(obj, "lines", 1)
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation)
                val edittext        = drawerEditText(lines, R.style.editTextParams, preOrientation)
                evt?.view = edittext
                add(box, label)
                add(box, edittext)
            }
            "dateedit"       -> { // Falta establecer como readonly
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation, 2.0f)
                val edittext        = drawerEditText(1, R.style.editTextParams, preOrientation, 3.0f)
                val image           = drawerImage(R.drawable.ic_datepicker, preOrientation, 1.0f)
                evt?.view = edittext
                add(box, label)
                add(box, edittext)
                add(box, image)
            }
            "timeedit"       -> { // Falta establecer como readonly
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation, 2.0f)
                val edittext        = drawerEditText(1, R.style.editTextParams, preOrientation, 3.0f)
                val image           = drawerImage(R.drawable.ic_timepicker, preOrientation, 1.0f)
                evt?.view = edittext
                add(box, label)
                add(box, edittext)
                add(box, image)
            }
            "label"       -> {
                drawerLabel(description, R.style.labelParams, null)
            }
            "listbox"       -> {
                val spinnerArray    = getSelectableItems(obj, "items")
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation)
                val spinner         = drawerListbox(preOrientation)
                LoadSpin(context, spinner).run {
                    setData(spinnerArray)
                    selectedItemStyle = R.style.spinnerParams
                    Load()
                }
                evt?.view = spinner
                add(box, label)
                add(box, spinner)
            }
            "checkbox"      -> { // Faltan eventos
                val itemsArray      = getSelectableItems(obj, "items")
                val preOrientation  = getOrientation(obj, "orientation", "horizontal")
                val itemsOrientation= getOrientation(obj, "itemsOrientation", false)
                val box             = drawerBoxInput(preOrientation)
                var weigth:Float?   = null
                if(description.isNotEmpty()) {
                    val label = drawerLabel(description, R.style.labelParams, preOrientation)
                    add(box, label)
                    weigth = 2.0f
                }
                val group           = drawerGroup(itemsOrientation, preOrientation, weigth)
                for(item in itemsArray){
                    group.addView(drawerCheckBox(item.id, item.value!!, null))
                }
                add(box, group)
            }
            "radiobutton"      -> { // Faltan eventos
                val itemsArray      = getSelectableItems(obj, "items")
                val preOrientation  = getOrientation(obj, "orientation", "horizontal")
                val itemsOrientation= getOrientation(obj, "itemsOrientation", false)
                val box             = drawerBoxInput(preOrientation)
                var weigth:Float?   = null
                if(description.isNotEmpty()){
                    val label = drawerLabel(description, R.style.editTextParams, preOrientation)
                    add(box, label)
                    weigth = 2.0f
                }
                val group           = drawerRadioGroup(itemsOrientation, preOrientation, weigth)
                for(item in itemsArray){
                    group.addView(drawerRadioButton(item.id, item.value!!, null))
                }
                add(box, group)
            }
            "switch"    -> { // Faltan eventos
                Switch(context).apply {
                    this.text       = description
                    this.textOff    = pablo.molina.jsonform.Json.getText(obj, "textOff")
                    this.textOn     = pablo.molina.jsonform.Json.getText(obj, "textOn")
                }
            }
            "button"        -> { // Falta completar
                drawerButton().apply {
                    this.text = description
                    evt?.view = this
                }
            }
            else -> View(context)
        }?.apply {
            if(id!=0) {
                setId(id)
            }
            if(mandatory){
                mandatoryFields?.add(this)
            }
            if(evt!=null) {
                events.add(evt)
            }
        }
        return widget
    }



    private fun getEvent(obj: JSONObject?): JFormEvent?{
        val event = pablo.molina.jsonform.Json.getObject(obj!!, "event")
        var jevent: JFormEvent? = null
        if(event!=null){
            jevent = JFormEvent()
            val type = pablo.molina.jsonform.Json.getText(event,"type")
            jevent.type = when(type){
                "change"    -> JFormEvent.Type.CHANGE
                "click"     -> JFormEvent.Type.CLICK
                "load"      -> JFormEvent.Type.LOAD
                else -> null
            }

            val actions = pablo.molina.jsonform.Json.getArray(event, "actions")
            if(actions!=null){
                val size = actions.length()
                for (a in 0 until size){
                    val sobj    = actions.getJSONObject(a)
                    val actObj  = pablo.molina.jsonform.Json.getObject(sobj, "action")
                    val atype   = pablo.molina.jsonform.Json.getText(actObj, "type")
                    val apredef = pablo.molina.jsonform.Json.getText(actObj, "predefined")
                    val acontid = pablo.molina.jsonform.Json.getInt(actObj, "containerId", 0)
                    val act = JFormAction()
                    act.containerId = acontid
                    act.predefined  = apredef
                    act.type        = when(atype){
                        "addMandatory"      -> JFormAction.Type.ADD_MANDANTORY
                        "disable"           -> JFormAction.Type.DISABLE
                        "enable"            -> JFormAction.Type.ENABLE
                        "hide"              -> JFormAction.Type.HIDE
                        "open"              -> JFormAction.Type.OPEN
                        "rmMandatory"       -> JFormAction.Type.RM_MANDATORY
                        "set"               -> JFormAction.Type.SET
                        "show"              -> JFormAction.Type.SHOW
                        "unset"             -> JFormAction.Type.UNSET
                        else -> null
                    }
                    jevent.actions.add(act)
                }
            }

            val conditions = pablo.molina.jsonform.Json.getObject(event, "conditions")
            if(conditions!=null){
                val acond   = pablo.molina.jsonform.Json.getText(conditions, "condition")
                val avalue  = pablo.molina.jsonform.Json.getText(conditions, "value")
                val aitems  = pablo.molina.jsonform.Json.getArray(conditions, "items")
                val jcond   = JFormCondition()
                jcond.type        = when(acond){
                    "ET"      -> JFormCondition.Type.ET
                    "GT"      -> JFormCondition.Type.GT
                    "LT"      -> JFormCondition.Type.LT
                    "NE"      -> JFormCondition.Type.NE
                    else -> null
                }
                jcond.valor0 = avalue

                if(aitems!=null){
                    val size = aitems.length()
                    for (a in 0 until size){
                        val item    = aitems[a] as Int
                        jcond.items.add(item)
                    }
                    jevent.condition = jcond
                }
            }
        }
        return jevent
    }

    private fun getSelectableItems(obj: JSONObject?, key:String):ArrayList<SelectableItem>{
        val itemsObj        = pablo.molina.jsonform.Json.getArray(obj, key)
        val spinnerArray = ArrayList<SelectableItem>()
        for(x in 0 until itemsObj!!.length()){
            val obj     = pablo.molina.jsonform.Json.getObject(itemsObj.get(x).toString())
            val id      = pablo.molina.jsonform.Json.getInt(obj,"id", 0)
            val value   = pablo.molina.jsonform.Json.getText(obj,"value")
            spinnerArray.add(SelectableItem(value, id))
        }
        return spinnerArray
    }

    private fun getOrientation(obj: JSONObject?, key:String, opposite:Boolean):String{
        val orientation     = pablo.molina.jsonform.Json.getText(obj, key)
        return if (orientation.isEmpty()) {
            Layout.procOrientation(context, opposite)
        }else{
            orientation
        }
    }
    private fun getOrientation(obj: JSONObject?, key:String, default:String):String{
        val orientation     = pablo.molina.jsonform.Json.getText(obj, key)
        return if (orientation.isEmpty()) {
            default
        }else{
            orientation
        }
    }

    fun getType(type:String):ContainerType?{
        var resp:ContainerType? = null
        for(c in contBoxes){
            if(c==type){
                resp = ContainerType.BOX
            }
        }
        for(c in contWidgets){
            if(c==type){
                resp = ContainerType.WIDGET
            }
        }
        for(c in contSpecials){
            if(c==type){
                resp = ContainerType.SPECIAL
            }
        }
        for(c in contPredefined){
            if(c==type){
                resp = ContainerType.PREDEFINED
            }
        }
        return resp
    }

    fun add(parentView: View?, child:View?):View?{
        if(child!=null) {
            when (parentView) {
                is LinearLayout -> parentView.addView(child)
                is ScrollView -> parentView.addView(child)
                is RadioGroup -> parentView.addView(child)
                is TabLayout -> parentView.addView(child)
                else -> View(context)
            }
        }
        return parentView
    }
}

enum class ContainerType{
    BOX, WIDGET, SPECIAL, PREDEFINED
}

enum class JsonEvents{
    OMCHANGE, ONCLICK, ONLOAD
}

enum class JsonActions{
    SHOW, HIDE, OPEN, SET
}



class MyEvent(){

}
