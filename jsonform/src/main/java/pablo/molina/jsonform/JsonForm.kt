package pablo.molina.jsonform

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.*
import pablo.molina.jsonform.event.JFormAction
import pablo.molina.jsonform.event.JFormCondition
import pablo.molina.jsonform.event.JFormEvent
import pablo.molina.jsonform.spinner.LoadSpin
import pablo.molina.jsonform.spinner.SelectableItem
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import kotlin.random.Random

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

    val widgets:HashMap<Int, View> = HashMap()

    fun getFields():View{
        baseLayout = LinearLayout(context)
        //var mv = View(context)
        val size = jsonArray.length()
        for (a in 0 until size){
            val obj         = jsonArray.getJSONObject(a)
            val container   = Json.getObject(obj,"container")

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
        val type        = Json.getText(obj,"type")
        val container   = Json.getObject(obj, "container")
        val childs      = Json.getArray(obj, "_childs")

        val c:View? = when(getType(type)){
            ContainerType.BOX ->{
                var linear = someProc("afterscroll", obj)
                if(childs!=null){
                    val size = childs.length()
                    for (a in 0 until size){
                        val sobj    = childs.getJSONObject(a)
                        val cChild  = Json.getObject(sobj, "container")
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
                null//TabItem(context)
            }
            "tablayout"     -> {
                null//TabLayout(context)
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
        val description     = Json.getText(obj, "description")
        val id              = Json.getInt(obj, "id", 0)
        val mandatory       = Json.getBoolean(obj, "mandatory")
        var evt             = getEvent(obj)

        val container = when(type){
            "title"       -> {
                drawerTitle(id, description).apply {
                    setTextAppearance(context, R.style.labelTitle)
                    widgets[id] = this
                }
            }
            "textedit"       -> {
                val lines           = Json.getInt(obj, "lines", 1)
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation)
                val edittext        = drawerEditText(lines, R.style.editTextParams, preOrientation)
                evt?.view = edittext
                widgets[id] = edittext
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
                widgets[id] = edittext
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
                widgets[id] = edittext
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
                widgets[id] = spinner
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
                    val checkox = drawerCheckBox(id, item.id.toString(), item.value!!, null)
                    val evt0 = getGroupEvent("multiple_tag")
                    evt0.view = checkox
                    events.add(evt0)

                    //widgets[id] = checkox
                    group.addView(checkox)
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

                // Evento de almacenamiento
                val evt0 = getGroupEvent("single_tag")
                evt0.view = group
                events.add(evt0)

                widgets[id] = group
                add(box, group)
            }
            "switch"    -> { // Faltan eventos
                drawerSwitch(
                    description,
                    Json.getText(obj, "textOff"),
                    Json.getText(obj, "textOn")
                ).apply {
                    widgets[id] = this
                }
            }
            "button"        -> { // Falta completar
                drawerButton().apply {
                    this.text = description
                    evt?.view = this
                    widgets[id] = this
                }
            }
            else -> View(context)
        }?.apply {
            /*if(id!=0) {
                setId(id)
            }*/

            if(mandatory){
                mandatoryFields?.add(this)
            }
            if(evt!=null) {
                events.add(evt)
            }
        }
        return container
    }

    private fun getGroupEvent(predefined:String):JFormEvent{
        val event = JFormEvent()
        event.type = JFormEvent.Type.CHANGE

        val act = JFormAction()
        act.predefined  = predefined
        act.type = JFormAction.Type.STORE

        event.actions.add(act)
        return event
    }

    fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }


    private fun getEvent(obj: JSONObject?): JFormEvent?{
        val event = Json.getObject(obj!!, "event")
        var jevent: JFormEvent? = null
        if(event!=null){
            // Evento
            jevent = JFormEvent()
            val type = Json.getText(event,"type")
            jevent.type = when(type){
                "change"    -> JFormEvent.Type.CHANGE
                "click"     -> JFormEvent.Type.CLICK
                "load"      -> JFormEvent.Type.LOAD
                else -> null
            }

            // Acciones
            val actions = Json.getArray(event, "actions")
            if(actions!=null){
                val size = actions.length()
                for (a in 0 until size){
                    val sobj    = actions.getJSONObject(a)
                    val actObj  = Json.getObject(sobj, "action")
                    val atype   = Json.getText(actObj, "type")
                    val apredef = Json.getText(actObj, "predefined")
                    val acontid = Json.getInt(actObj, "containerId", 0)
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

            // Condicion
            val conditions = Json.getObject(event, "conditions")
            if(conditions!=null){
                val acond   = Json.getText(conditions, "condition")
                val avalue  = Json.getText(conditions, "value")
                val aitems  = Json.getArray(conditions, "items")
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
        val itemsObj        = Json.getArray(obj, key)
        val spinnerArray = ArrayList<SelectableItem>()
        for(x in 0 until itemsObj!!.length()){
            val obj     = Json.getObject(itemsObj.get(x).toString())
            val id      = Json.getInt(obj,"id", 0)
            val value   = Json.getText(obj,"value")
            spinnerArray.add(SelectableItem(value, id))
        }
        return spinnerArray
    }

    private fun getOrientation(obj: JSONObject?, key:String, opposite:Boolean):String{
        val orientation     = Json.getText(obj, key)
        return if (orientation.isEmpty()) {
            Layout.procOrientation(context, opposite)
        }else{
            orientation
        }
    }
    private fun getOrientation(obj: JSONObject?, key:String, default:String):String{
        val orientation     = Json.getText(obj, key)
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
                //is TabLayout -> parentView.addView(child)
                else -> View(context)
            }
        }
        return parentView
    }
}

enum class ContainerType{
    BOX, WIDGET, SPECIAL, PREDEFINED
}
