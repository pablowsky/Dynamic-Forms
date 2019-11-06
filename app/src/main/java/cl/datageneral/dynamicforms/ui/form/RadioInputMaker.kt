package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.content.res.Configuration
import android.widget.*
import pablo.molina.jsonform.spinner.SelectableItem

/**
 * Created by Pablo Molina on 11-10-2019. s.pablo.molina@gmail.com
 */
class RadioInputMaker(val label:String?, val  items:ArrayList<SelectableItem>, val context: Context):BaseMaker(context){
	var baseLayout 					= LinearLayout(context)
	var labelObj: 		TextView? 	= null
	var radioGroup: 	RadioGroup?	= null
	var radioGroupOrientation 		= RadioGroup.HORIZONTAL

	fun getObject(): LinearLayout {
		pLayout()
		pBox()
		pLabel()
		pRadioGroup()
		for(item: SelectableItem in items){
			pRadioButton(item)
		}
		join()
		return baseLayout
	}

	private fun pLayout(){
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			labelParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			rgroupParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			layoutOrientation = LinearLayout.VERTICAL

			radioParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
			)
			radioGroupOrientation 		= RadioGroup.HORIZONTAL
		}else{
			labelParams 	= LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
			rgroupParams 	= LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f)

			layoutOrientation = LinearLayout.HORIZONTAL

			radioParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			radioGroupOrientation 		= RadioGroup.VERTICAL
		}
	}

	private fun pBox(){
		baseLayout.run{ // Cambiar por with{}
			orientation 	= layoutOrientation
			layoutParams 	= layoutParamsX
			setPadding(
					boxPadding[0],
					boxPadding[1],
					boxPadding[2],
					boxPadding[3])
			//setBackgroundResource(R.color.iconOrange) // Temporal
		}
	}

	private fun pLabel(){
		if(label!=null){
			val t = LabelMaker(label, context)
			t.setLayoutParams(labelParams)
			labelObj = t.getObject()
			//labelObj!!.setBackgroundResource(R.color.iconRed) // Temporal
		}
	}

	private fun pRadioGroup(){
		radioGroup = RadioGroup(context)
		radioGroup?.run {
			layoutParams = rgroupParams
			//setBackgroundResource(R.color.iconGreen) // Temporal
			orientation = radioGroupOrientation
		}
	}

	private fun pRadioButton(item: SelectableItem){
		val radioInput 	= RadioButton(context)
		radioInput.text = item.value
		radioInput.tag 	= item.id
		radioInput.run {
			layoutParams = radioParams
		}
		//radioInput.setBackgroundResource(R.color.colorGrey_1) // Temporal
		radioGroup?.addView(radioInput)
	}

	private fun join(){
		if(labelObj!=null){
			baseLayout.addView(labelObj)
		}

		if(radioGroup!=null){
			baseLayout.addView(radioGroup)
		}
	}
}

