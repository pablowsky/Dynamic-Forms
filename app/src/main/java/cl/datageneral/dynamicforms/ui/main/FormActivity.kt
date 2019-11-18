package cl.datageneral.dynamicforms.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import cl.datageneral.dynamicforms.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_form.view.*
import kotlinx.android.synthetic.main.app_gp_btns.*
import org.json.JSONArray
import pablo.molina.jsonform.FormStyles
import pablo.molina.jsonform.Json
import pablo.molina.jsonform.JsonForm
import pablo.molina.jsonform.JsonFormInflate
import java.io.IOException
import javax.inject.Inject


class FormActivity : DaggerAppCompatActivity(), FormContract.View {

    var my_var:JsonForm? = null

    @Inject
    lateinit var presenter: FormPresenter<FormContract.View>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        presenter.onAttach(this)
        getFormConfig() // Esto estara en la capa de datos
        presenter.start()

        btnGuardar.setOnClickListener{
            val my_serializer = my_var!!.getSerializer("myForm")
            my_serializer.checkForm()
            val obj =   my_serializer.getValues()

            Log.e("Obj", obj.toString(4))
        }

        //my_layout.spinner.background = getDrawable(R.drawable.spinner)
        val style = FormStyles("myForm")
        style.spinnerError = getDrawable(R.drawable.spinner)
        style.spinnerNormal = getDrawable(R.drawable.spinner)
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
        val mStyles = FormStyles("").apply {
            spinnerNormal       =   getDrawable(R.drawable.spinner)
            spinnerError        =   getDrawable(R.drawable.spinner_error)
            spinnerSelected     =   R.style.spinnerParams
            dateImage           =   R.drawable.ic_datepicker
            dateBackgroundColor =   resources.getColor(android.R.color.transparent)
            timeImage           =   R.drawable.ic_timepicker
            timeBackgroundColor =   resources.getColor(android.R.color.transparent)
            editTextNormal      =   R.style.editTextParams
            editTextError       =   R.style.editTextParams
            labelNormal         =   R.style.labelParams
            labelError          =   R.style.labelParams
            title               =   R.style.labelTitle
        }

        my_var = JsonForm(array, this, mStyles)
        my_var!!.loadJsonForm("myForm", my_layout)
        my_var!!.startJsonEvents("myForm")


        val saved = "{\"19\": \"y colaboración prestada y quedó atenta a\", \"6\": \"15\\/11\\/2019\", \"7\": \"11:20\", \"20\": [\"3\"], \"11\": [\"3\"], \"9\": [\"1\",\"2\"], \"15\": [\"3\"]}"
        val objSaved = Json.getObject(saved)
        //val inflater = JsonFormInflate(my_var!!)
        //inflater.init(objSaved!!)

    }
}
