package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.view.View
import android.widget.*
import cl.datageneral.dynamicforms.R
import cl.datageneral.dynamicforms.ui.form.spinner.LoadSpin
import cl.datageneral.dynamicforms.ui.form.spinner.SelectableItem
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
        val description     = Json.getText(obj, "description")
        val id              = Json.getInt(obj, "id", 0)
        val mandatory       = Json.getBoolean(obj, "mandatory")
        val widget = when(type){
            "title"       -> {
                drawerTitle(id, description)
            }
            "textedit"       -> {
                val lines           = Json.getInt(obj, "lines", 1)
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation)
                val edittext        = drawerEditText(lines, R.style.editTextParams, preOrientation)
                add(box, label)
                add(box, edittext)
            }
            "dateedit"       -> {
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation, 2.0f)
                val edittext        = drawerEditText(1, R.style.editTextParams, preOrientation, 3.0f)
                val image           = drawerImage(R.drawable.ic_datepicker, preOrientation, 1.0f)
                add(box, label)
                add(box, edittext)
                add(box, image)
            }
            "timeedit"       -> {
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.labelParams, preOrientation, 2.0f)
                val edittext        = drawerEditText(1, R.style.editTextParams, preOrientation, 3.0f)
                val image           = drawerImage(R.drawable.ic_timepicker, preOrientation, 1.0f)
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
                add(box, label)
                add(box, spinner)
            }
            "checkbox"      -> {
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
            "radiobutton"      -> {
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
            "switch"    -> {
                Switch(context).apply {
                    this.text       = description
                    this.textOff    = Json.getText(obj, "textOff")
                    this.textOn     = Json.getText(obj, "textOn")
                }
            }
            "button"        -> {
                Button(context)
            }
            else -> View(context)
        }?.apply {
            if(id!=0) {
                setId(id)
            }
            if(mandatory){
                mandatoryFields?.add(this)
            }
        }
        return widget
    }

    private fun getEvent(obj: JSONObject?){

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
