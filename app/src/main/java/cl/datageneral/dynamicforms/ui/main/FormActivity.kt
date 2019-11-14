package cl.datageneral.dynamicforms.ui.main

import android.os.Bundle
import android.util.Log
import cl.datageneral.dynamicforms.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.app_gp_btns.*
import org.json.JSONArray
import pablo.molina.jsonform.JsonForm
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
            val my_serializer = my_var!!.getSerializer("name")
            val obj =my_serializer.getValues()

            Log.e("Obj", obj.toString(4))
        }
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
        my_var = JsonForm(array, this)
        my_var!!.loadJsonForm("name", my_layout)
        my_var!!.startJsonEvents("name")
    }
}
