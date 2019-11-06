package cl.datageneral.dynamicforms.ui.main

import android.os.Bundle
import android.util.Log
import cl.datageneral.dynamicforms.R
import cl.datageneral.dynamicforms.ui.DaggerJFormActivity
import pablo.molina.jsonform.spinner.SelectableItem
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONArray
import pablo.molina.jsonform.JsonForm
import java.io.IOException
import javax.inject.Inject


class FormActivity : DaggerJFormActivity(), FormContract.View {

    @Inject
    lateinit var presenter: FormPresenter<FormContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        presenter.onAttach(this)
        getFormConfig() // Esto estara en la capa de datos
        presenter.start()

    }

    fun getFormConfig(){ // Esto estara en la capa de datos
        var json: String? = null
        try {
            val myform =  assets.open("simpleform.json")
            val mbuffer = ByteArray(myform.available())
            myform.read(mbuffer)
            myform.close()

            //json = mbuffer.toString()

            json = String(mbuffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        presenter.jsonArray = JSONArray(json)
    }

    override fun loadForm(array: JSONArray){
        val som = JsonForm(array, my_layout, this)
        som.getFields()
        if(som.events.size > 0){
            for(event in som.events){
                Log.e("loadForm","Type: "+event.type)
                startEvent(event)
            }
        }
    }



    override fun mkTextInput(label:String?, value:String, lines:Int){
        /*Log.e("mkTextInput",label)
        val f = TextInputMaker(label, value, lines, this)
        my_layout.addView(f.getObject())*/
    }

    override fun mkListInput(label: String?, items:ArrayList<SelectableItem>) {
        /*Log.e("mkTextInput",label)
        val f = ListInputMaker(label, items, this)
        f.defaultItem = SelectableItem("Seleccione", 0)
        my_layout.addView(f.getObject())*/
    }

    override fun mkRadioInput(label: String?, items:ArrayList<SelectableItem>) {
        /*Log.e("mkTextInput",label)
        val f = RadioInputMaker(label, items, this)
        //f.defaultItem = SelectableItem("Seleccione", 0)
        my_layout.addView(f.getObject())*/
    }

    override fun mkCheckInput(label: String?, items:ArrayList<SelectableItem>) {
        /*Log.e("mkTextInput",label)
        val f = CheckInputMaker(label, items, this)
        //f.defaultItem = SelectableItem("Seleccione", 0)
        my_layout.addView(f.getObject())*/
    }
}
