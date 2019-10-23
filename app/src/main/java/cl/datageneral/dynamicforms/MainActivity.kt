package cl.datageneral.dynamicforms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cl.datageneral.dynamicforms.data.db.Query
import cl.datageneral.dynamicforms.data.db.models.Field
import cl.datageneral.dynamicforms.data.db.models.FieldType
import cl.datageneral.dynamicforms.data.db.models.Form
import cl.datageneral.dynamicforms.data.db.models.ItemsField
import cl.datageneral.dynamicforms.ui.main.FormActivity
import io.realm.kotlin.createObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val q= Query()
        val realm = q.realm

        try {
            realm.executeTransaction { realm ->

                val norma = realm.createObject<Form>(1)

                // Tipo de campo
                val ft1 = realm.createObject<FieldType>(1)
                ft1.description = "Campo de Texto"

                val ft2 = realm.createObject<FieldType>(2)
                ft2.description = "Area de Texto"

                val ft3 = realm.createObject<FieldType>(3)
                ft3.description = "Lista desplegable"

                val ft4 = realm.createObject<FieldType>(4)
                ft4.description = "Opcion de seleccion"

                val ft5 = realm.createObject<FieldType>(5)
                ft5.description = "Casilla de seleccion"

                // Campos para Normas
                val cm = realm.createObject<Field>(10)
                cm.name = "Campo de texto a ingresar"
                cm.fieldtype 	= 	ft1
                cm.mandatory 	= 	true
                cm.length	 	=	10

                val cm2 = realm.createObject<Field>(12)
                cm2.name = "2 Campo de texto a ingresar"
                cm2.fieldtype 	= 	ft1
                cm2.mandatory 	= 	false
                cm2.length	 	=	5

                val cm3 = realm.createObject<Field>(13)
                cm3.name = "Area de texto"
                cm3.fieldtype 	= 	ft2
                cm3.mandatory 	= 	true
                cm3.length	 	=	100

                val cm4 = realm.createObject<Field>(14)
                cm4.name = "2 Area de texto"
                cm4.fieldtype 	= 	ft2
                cm4.mandatory 	= 	false
                cm4.length	 	=	100

                // Lista de seleccion 1
                val itm = realm.createObject<ItemsField>(21)
                itm.value = "Item medio 1"
                itm.nonconformity = false

                val itm2 = realm.createObject<ItemsField>(22)
                itm2.value = "Item medio 2"
                itm2.nonconformity = true

                val itm3 = realm.createObject<ItemsField>(23)
                itm3.value = "Item medio 3"
                itm3.nonconformity = false

                val cm5 = realm.createObject<Field>(15)
                cm5.name = "Mi lista de seleccion"
                cm5.fieldtype 	= 	ft3
                cm5.mandatory 	= 	false
                cm5.items.add(itm)
                cm5.items.add(itm2)
                cm5.items.add(itm3)

                // Lista de seleccion 2
                val itm4 = realm.createObject<ItemsField>(24)
                itm4.value = "Item medio 4"
                itm4.nonconformity = false

                val itm5 = realm.createObject<ItemsField>(25)
                itm5.value = "Item medio 5"
                itm5.nonconformity = true

                val itm6 = realm.createObject<ItemsField>(26)
                itm6.value = "Item medio 6"
                itm6.nonconformity = false

                val cm6 = realm.createObject<Field>(16)
                cm6.name = "Mi lista de seleccion 2"
                cm6.fieldtype 	= 	ft3
                cm6.mandatory 	= 	true
                cm6.items.add(itm4)
                cm6.items.add(itm5)
                cm6.items.add(itm6)



                // Boton de opcion 1
                val itm7 = realm.createObject<ItemsField>(27)
                itm7.value = "Item medio "
                itm7.nonconformity = false

                val itm8 = realm.createObject<ItemsField>(28)
                itm8.value = "Item medio "
                itm8.nonconformity = true

                val itm9 = realm.createObject<ItemsField>(29)
                itm9.value = "Item medio "
                itm9.nonconformity = false

                val cm7 = realm.createObject<Field>(17)
                cm7.name = "Boton de opcion 1"
                cm7.fieldtype 	= 	ft4
                cm7.mandatory 	= 	true
                cm7.items.add(itm7)
                cm7.items.add(itm8)
                cm7.items.add(itm9)

                // Boton de opcion 2
                val itm10 = realm.createObject<ItemsField>(31)
                itm10.value = "Item medio "
                itm10.nonconformity = false

                val itm11 = realm.createObject<ItemsField>(32)
                itm11.value = "Item medio "
                itm11.nonconformity = true

                val itm12 = realm.createObject<ItemsField>(33)
                itm12.value = "Item medio "
                itm12.nonconformity = false

                val cm8 = realm.createObject<Field>(18)
                cm8.name = "Boton de opcion 2"
                cm8.fieldtype 	= 	ft4
                cm8.mandatory 	= 	true
                cm8.items.add(itm10)
                cm8.items.add(itm11)
                cm8.items.add(itm12)

                // Casilla de seleccion 1
                val itm13 = realm.createObject<ItemsField>(34)
                itm13.value = "Item medio "
                itm13.nonconformity = false

                val itm14 = realm.createObject<ItemsField>(35)
                itm14.value = "Item medio "
                itm14.nonconformity = true

                val itm15 = realm.createObject<ItemsField>(36)
                itm15.value = "Item medio "
                itm15.nonconformity = false

                val cm9 = realm.createObject<Field>(19)
                cm9.name = "Casilla de seleccion 1"
                cm9.fieldtype 	= 	ft5
                cm9.mandatory 	= 	true
                cm9.items.add(itm13)
                cm9.items.add(itm14)
                cm9.items.add(itm15)

                // Casilla de seleccion 2
                val itm16 = realm.createObject<ItemsField>(37)
                itm16.value = "Item medio "
                itm16.nonconformity = false

                val itm17 = realm.createObject<ItemsField>(38)
                itm17.value = "Item medio "
                itm17.nonconformity = true

                val itm18 = realm.createObject<ItemsField>(39)
                itm18.value = "Item medio "
                itm18.nonconformity = false

                val cm10 = realm.createObject<Field>(20)
                cm10.name = "Casilla de seleccion 2"
                cm10.fieldtype 	= 	ft5
                cm10.mandatory 	= 	true
                cm10.items.add(itm16)
                cm10.items.add(itm17)
                cm10.items.add(itm18)

                norma.fields.add(cm)
                norma.fields.add(cm2)
                norma.fields.add(cm3)
                norma.fields.add(cm4)
                norma.fields.add(cm5)
                norma.fields.add(cm6)
                norma.fields.add(cm7)
                norma.fields.add(cm8)
                norma.fields.add(cm9)
                norma.fields.add(cm10)


            }
        }catch (e: Exception){
            Log.e("QueryRealm",e.toString())
        }

        val intent = Intent(this, FormActivity::class.java)
        startActivity(intent)
    }
}
