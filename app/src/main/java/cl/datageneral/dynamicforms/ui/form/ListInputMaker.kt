package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Spinner
import cl.datageneral.dynamicforms.R
import pablo.molina.jsonform.spinner.LoadSpin
import pablo.molina.jsonform.spinner.SelectableItem


/**
 * Created by Pablo Molina on 10-10-2019. s.pablo.molina@gmail.com
 */
class ListInputMaker(val label:String?, val  items:ArrayList<SelectableItem>, val context: Context): BaseMaker(context){
	var baseLayout 				= LinearLayout(context)
	var labelObj: TextView? 	= null
	var spinner: Spinner? 		= null
	var defaultItem: SelectableItem?= null

	fun getObject(): LinearLayout {
		pBox()
		pLabel()
		pSpinner()
		join()
		return baseLayout
	}

	private fun pBox(){
		baseLayout.run {
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

	private fun pSpinner(){
		spinner = Spinner(context)
		spinner?.run{
			setBackgroundResource(R.color.iconYellow) // Temporal
			setPadding(
					spinnerPadding[0],
					spinnerPadding[1],
					spinnerPadding[2],
					spinnerPadding[3]
			)
			layoutParams = etextParams
		}
		LoadSpin(context, spinner!!).run {
			setData(items)
			includeFirstRow(defaultItem!!)
			selectedItemStyle = R.style.spinnerParams
			//setSelected(idInstalacion)
			Load()
		}
	}

	private fun join(){
		if(labelObj!=null){
			baseLayout.addView(labelObj)
		}

		if(spinner!=null){
			baseLayout.addView(spinner)
		}
	}
}