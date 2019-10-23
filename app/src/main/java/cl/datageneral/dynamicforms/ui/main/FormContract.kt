package cl.datageneral.dynamicforms.ui.main

import cl.datageneral.dynamicforms.ui.base.BaseContract
import cl.datageneral.dynamicforms.ui.form.spinner.SelectableItem
import org.json.JSONArray

/**
 * Created by Pablo Molina on 14-10-2019. s.pablo.molina@gmail.com
 */
class FormContract {

    interface View: BaseContract.View{
        fun mkTextInput(label:String?, value:String, lines:Int)
        fun mkListInput(label:String?, items:ArrayList<SelectableItem>)
        fun mkRadioInput(label: String?, items:ArrayList<SelectableItem>)
        fun mkCheckInput(label: String?, items:ArrayList<SelectableItem>)
        fun loadForm(array: JSONArray)
    }

    interface Presenter{
        fun start()
    }
}
