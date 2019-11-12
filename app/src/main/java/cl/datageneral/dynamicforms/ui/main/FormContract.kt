package cl.datageneral.dynamicforms.ui.main

import cl.datageneral.dynamicforms.ui.base.BaseContract
import pablo.molina.jsonform.spinner.SelectableItem
import org.json.JSONArray

/**
 * Created by Pablo Molina on 14-10-2019. s.pablo.molina@gmail.com
 */
class FormContract {

    interface View: BaseContract.View{
        fun loadForm(array: JSONArray)
    }

    interface Presenter{
        fun start()
    }
}
