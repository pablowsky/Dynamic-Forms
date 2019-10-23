package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import cl.datageneral.dynamicforms.R

/**
 * Created by Pablo Molina on 10-10-2019. s.pablo.molina@gmail.com
 */
class LabelMaker(label:String, context: Context):BaseMaker(context){
	var base = TextView(context)

	init {

		base.text = label
		base.setTextAppearance(context, R.style.labelParams)
		base.layoutParams = labelParams

	}

	fun setLayoutParams(labelParams:LinearLayout.LayoutParams){
		base.layoutParams = labelParams
	}

	fun getObject(): TextView {
		return base
	}
}