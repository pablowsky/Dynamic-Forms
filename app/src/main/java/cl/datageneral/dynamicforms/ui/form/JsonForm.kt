package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
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

    private val contBoxes       = arrayOf("linearlayout","scrolllayout","cardbox")
    private val contWidgets     = arrayOf("textedit","dateedit","timeedit","listbox","checkbox","radiobutton","button")
    private val contSpecials    = arrayOf("tablayout","tabitem")
    private val contPredefined  = arrayOf("add_items","check_items","pictures_gallery")

    fun getFields():View{
        baseLayout = LinearLayout(context)
        //var mv = View(context)
        val size = jsonArray.length()
        for (a in 0 until size){
            val obj         = jsonArray.getJSONObject(a)
            val container   = Json.getObject(obj,"container")

            /*Log.e("obj", obj.toString())
            Log.e("container", container.toString())*/

            extractObj(container!!, mainLayout)
            //val usage       = Json.getText(container!!,"usage")

           // if(usage == "main" ){
            //    mv = extractObj(obj, mainLayout)
            //}
        }



        /*val a = baseLayout.childCount
        Log.e("childCount1", "-$a")
        if(mainLayout is LinearLayout) {
            for(b in 0 until a){
                val v = baseLayout.getChildAt(a)
                mainLayout.addView(v)
            }
        }*/
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


        Log.e("extractObj","obj:$obj")

        val c:View? = when(getType(type)){
            ContainerType.BOX ->{
                Log.e("extractObj","BOX")
                var linear = someProc("linearlayout", obj)
                if(childs!=null){
                    val size = childs.length()
                    for (a in 0 until size){
                        val sobj = childs.getJSONObject(a)
                        val cChild   = Json.getObject(sobj, "container")

                        linear = extractObj(cChild!!, linear)
                    }
                }
                val scroll = someProc(type, obj)
                add(scroll, linear)
            }
            ContainerType.WIDGET -> {
                Log.e("extractObj","WIDGET")
                someProc(type, obj)
            }
            /*ContainerType.SPECIAL ->
            ContainerType.PREDEFINED ->*/
            else -> {
                Log.e("extractObj","No se pudo detectar el tipo")
                //View(context)
                null
            }
        }
        return if(c==null){
            parentView
        }else{
            add(parentView, c)
        }
        //return add(parentView, c)
    }

    /*fun some(obj:JSONObject, parentView: View):View {
        val type = Json.getText(obj, "type")
        val child = someProc(type, obj)
        return add(parentView, child)
    }*/
    private fun someProc(type:String, obj: JSONObject?):View?{
        val width           = Json.getText(obj, "width")// Obsoleto
        val height          = Json.getText(obj, "height")// Obsoleto
        val mOrientation    = Json.getText(obj, "orientation")// Obsoleto
        val objOrientation  = getOrientationParam(mOrientation)// Obsoleto
        val objParamsLayout = getLayoutParams(width, height) // Obsoleto

        //val drawer = JsonFormDrawer(context)
        Log.e("someProc", type+"--"+obj.toString())

        return when(type){
            "boxInput"  -> {
                drawerBoxInput(null)
            }
            "linearlayout"  -> {
                LinearLayout(context).apply {
                    orientation     = LinearLayout.VERTICAL
                    layoutParams    = Layout.p3
                }
            }
            "scrolllayout"  -> {
                drawerScroll()
            }
            "textedit"       -> {
                val lines           = Json.getInt(obj, "lines", 1)
                val description     = Json.getText(obj, "description")
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.editTextParams, preOrientation)
                val edittext        = drawerEditText(lines, R.style.editTextParams, preOrientation)
                add(box, label)
                add(box, edittext)
            }
            "dateedit"       -> {
                val description     = Json.getText(obj, "description")
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.editTextParams, preOrientation, 2.0f)
                val edittext        = drawerEditText(1, R.style.editTextParams, preOrientation, 3.0f)
                val image           = drawerImage(R.drawable.ic_datepicker, preOrientation, 1.0f)
                add(box, label)
                add(box, edittext)
                add(box, image)
            }
            "timeedit"       -> {
                val description     = Json.getText(obj, "description")
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.editTextParams, preOrientation, 2.0f)
                val edittext        = drawerEditText(1, R.style.editTextParams, preOrientation, 3.0f)
                val image           = drawerImage(R.drawable.ic_timepicker, preOrientation, 1.0f)
                add(box, label)
                add(box, edittext)
                add(box, image)
            }
            "label"       -> {
                val description = Json.getText(obj, "description")
                drawerLabel(description, R.style.editTextParams, null)
            }
            "listbox"       -> {
                val description     = Json.getText(obj, "description")
                val spinnerArray    = getSelectableItems(obj, "items")
                val preOrientation  = getOrientation(obj, "orientation", true)
                val box             = drawerBoxInput(preOrientation)
                val label           = drawerLabel(description, R.style.editTextParams, preOrientation)
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
                val description     = Json.getText(obj, "description")
                val itemsArray      = getSelectableItems(obj, "items")
                val preOrientation  = getOrientation(obj, "orientation", false)
                var itemsOrientation = Json.getText(obj, "itemsOrientation")
                val box             = drawerBoxInput(preOrientation)
                if(description.isNotEmpty()) {
                    val label = drawerLabel(description, R.style.editTextParams, preOrientation)
                    add(box, label)
                }
                if(itemsOrientation.isEmpty()){
                    itemsOrientation = "vertical"
                }
                val group           = drawerGroup(itemsOrientation, 2.0f)
                for(item in itemsArray){
                    group.addView(drawerCheckBox(item.id, item.value!!, null))
                }
                add(box, group)
            }
            "radiobutton"      -> {
                val description     = Json.getText(obj, "description")
                val itemsArray      = getSelectableItems(obj, "items")
                val preOrientation  = getOrientation(obj, "orientation", false)
                var itemsOrientation = Json.getText(obj, "itemsOrientation")
                val box             = drawerBoxInput(preOrientation)
                if(description.isNotEmpty()) {
                    val label = drawerLabel(description, R.style.editTextParams, preOrientation)
                    add(box, label)
                }
                if(itemsOrientation.isEmpty()){
                    itemsOrientation = "vertical"
                }
                val group           = drawerRadioGroup(itemsOrientation, 2.0f)
                for(item in itemsArray){
                    group.addView(drawerRadioButton(item.id, item.value!!, null))
                }
                add(box, group)
            }
            "switch"    -> {
                val description     = Json.getText(obj, "description")
                Switch(context).apply {
                    this.text       = description
                    this.textOff    = Json.getText(obj, "textOff")
                    this.textOn     = Json.getText(obj, "textOn")
                }
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
    private fun getOpposite(direction:String):String{
        return if(direction=="vertical"){
            "horizontal"
        }else{
            "vertical"
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

    @Deprecated("Funcion implementada en JsonFormDrawer")
    fun getOrientationParam(orientation: String):Int{
        return when(orientation){
            "vertical"      -> LinearLayout.VERTICAL
            "horizontal"    -> LinearLayout.HORIZONTAL
            else -> LinearLayout.HORIZONTAL
        }
    }

    @Deprecated("Funcion implementada en JsonFormDrawer")
    fun getLayoutParams(width:String, height:String):LinearLayout.LayoutParams{
        val objWidth = when(width){
            "match_parent"  -> LinearLayout.LayoutParams.MATCH_PARENT
            "wrap_content"  -> LinearLayout.LayoutParams.WRAP_CONTENT
            else -> LinearLayout.LayoutParams.WRAP_CONTENT
        }

        val objHeight = when(height){
            "match_parent"  -> LinearLayout.LayoutParams.MATCH_PARENT
            "wrap_content"  -> LinearLayout.LayoutParams.WRAP_CONTENT
            else -> LinearLayout.LayoutParams.WRAP_CONTENT
        }

        return LinearLayout.LayoutParams(objWidth, objHeight )
    }

    @Deprecated("Funcion implementada en JsonFormDrawer")
    fun getLayoutParams(type: String):LinearLayout.LayoutParams{
        return when(type){
            "label"  -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            "textedit"       -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f
            )
            "spinner"       -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f
            )
            "checkbox"      -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            "radiogroup"    -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f
            )
            "radiobutton"   -> LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            /*"tabitem"       -> {
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
            }*/
            else -> LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }
}

enum class ContainerType{
    BOX, WIDGET, SPECIAL, PREDEFINED
}
