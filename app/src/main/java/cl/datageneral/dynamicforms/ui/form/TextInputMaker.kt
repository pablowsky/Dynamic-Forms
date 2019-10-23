package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import cl.datageneral.dynamicforms.R

/**
 * Created by Pablo Molina on 10-10-2019. s.pablo.molina@gmail.com
 */
class TextInputMaker(val label:String?, val value:String, var lines:Int, val context: Context):BaseMaker(context){
	var baseLayout 				= LinearLayout(context)
	var labelObj:TextView? 		= null
	var edittextObj:EditText? 	= null

	fun getObject():LinearLayout{
		pBox()
		pLabel()
		pEditText()
		join()
		return baseLayout
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
		}
	}

	private fun pLabel(){
		if(label!=null){
			labelObj = LabelMaker(label, context).getObject()
		}
	}

	private fun pEditText(){
		edittextObj = EditText(context)
		edittextObj?.run {
			setText(value)
			setTextAppearance(context, R.style.editTextParams)
			layoutParams = etextParams
			setLines(lines)
		}
	}

	private fun join(){
		if(labelObj!=null){
			baseLayout.addView(labelObj)
		}

		if(labelObj!=null){
			baseLayout.addView(edittextObj)
		}
	}
}