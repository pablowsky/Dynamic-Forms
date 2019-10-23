package cl.datageneral.dynamicforms.ui.main

import android.util.Log
import cl.datageneral.dynamicforms.data.DataManager
import cl.datageneral.dynamicforms.ui.base.BasePresenter
import org.json.JSONArray
import javax.inject.Inject



/**
 * Created by Pablo Molina on 14-10-2019. s.pablo.molina@gmail.com
 */
class FormPresenter<V:FormContract.View> @Inject constructor(val dm: DataManager):
    BasePresenter<V>(),
    FormContract.Presenter{

    var jsonArray: JSONArray? = null

    override fun start() {
        /*Log.e("jsonaArray",jsonArray?.length().toString())
        val size = jsonArray?.length()
        for (a in 0 until size!!){
            val obj = jsonArray?.getJSONObject(a)

        }*/

        mView?.loadForm(jsonArray!!)
    }

    /*
        //answer = dm.getAnswer(idRequirement!!, idAudit!!)
        val fields = dm.getFields(1)
        //Log.e("fields",fields?.size.toString())
        for(f: Field in fields!!.iterator()){
            //Log.e("Field",f.name)
            if(f.fieldtype?.fieldTypeId==1){
                mView?.mkTextInput(f.name, "1", 1)
            }else if(f.fieldtype?.fieldTypeId==2){
                mView?.mkTextInput(f.name, "2", 3)
            }else if(f.fieldtype?.fieldTypeId==3){
                val spinnerArray = ArrayList<SelectableItem>()
                for(x:ItemsField in f.items){
                    spinnerArray.add(SelectableItem(x.value!!, x.itemsFieldId!!))
                }
                mView?.mkListInput(f.name, spinnerArray)
            }else if(f.fieldtype?.fieldTypeId==4){
                val spinnerArray = ArrayList<SelectableItem>()
                for(x:ItemsField in f.items){
                    spinnerArray.add(SelectableItem(x.value!!, x.itemsFieldId!!))
                }
                mView?.mkRadioInput(f.name, spinnerArray)
            }else if(f.fieldtype?.fieldTypeId==5){
                val spinnerArray = ArrayList<SelectableItem>()
                if(f.items.size>0){
                    for(x: ItemsField in f.items){
                        Log.e("ItemsField","-"+x.value.toString())
                        Log.e("ItemsField","-"+x.itemsFieldId.toString())
                        spinnerArray.add(SelectableItem(x.value!!, x.itemsFieldId!!))
                    }
                }
                mView?.mkCheckInput(f.name, spinnerArray)
            }
        }
    }*/
}