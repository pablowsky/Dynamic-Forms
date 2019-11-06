package cl.datageneral.dynamicforms.ui

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import pablo.molina.jsonform.event.JFormAction
import pablo.molina.jsonform.event.JFormEvent
import dagger.android.support.DaggerAppCompatActivity
import pablo.molina.jsonform.fragments.SelectDateFragment
import pablo.molina.jsonform.fragments.SelectTimeFragment

/**
 * Created by Pablo Molina on 06-11-2019. s.pablo.molina@gmail.com
 */
open class DaggerJFormActivity: DaggerAppCompatActivity(){

    fun startEvent(event: JFormEvent){
        when(event.type){
            JFormEvent.Type.CHANGE -> {}
            JFormEvent.Type.CLICK -> startEventClick(event)
            JFormEvent.Type.LOAD -> {}
        }
    }

    private fun startEventClick(event: JFormEvent){
        event.view!!.setOnClickListener { view ->
            when(event.view){
                is EditText ->  loadAction<EditText>(event.view!!, event.actions)
                is Spinner ->  loadAction<Spinner>(event.view!!, event.actions)
                is Button ->  loadAction<Button>(event.view!!, event.actions)
            }
        }
    }

    inline fun <reified T : View>loadAction(view: View, actions:MutableList<JFormAction>){

        for(action in actions){
            if(view is EditText){
                if(action.predefined=="select_date"){
                    val newFragment = SelectDateFragment(view)
                    newFragment.show(fragmentManager, "DatePicker")
                }else if(action.predefined=="select_time"){
                    val newFragment = SelectTimeFragment(view)
                    newFragment.show(fragmentManager, "TimePicker")
                }
            }
            // Otras acciones

        }
    }

    inline fun <reified T : View>testFunction(view: View):Int{
        //SelectDateFragment(view as T::class)

        when (T::class){
            EditText::class -> {
                Log.e("testFunction", "EditText")}
            Spinner::class  -> {
                Log.e("testFunction", "Spinner")}
            Button::class   -> {
                Log.e("testFunction", "Button")}
            else -> {
                Log.e("testFunction", "Nulo")}
        }

        return 1
    }
}