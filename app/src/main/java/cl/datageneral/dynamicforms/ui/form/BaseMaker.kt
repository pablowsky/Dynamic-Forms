package cl.datageneral.dynamicforms.ui.form

import android.widget.LinearLayout
import android.content.Context
import android.content.res.Configuration


/**
 * Created by Pablo Molina on 10-10-2019. s.pablo.molina@gmail.com
 */
open class BaseMaker(context: Context){
	var orientation = context.resources.configuration.orientation

	var rgroupParams:		LinearLayout.LayoutParams
	var spinnerParams:		LinearLayout.LayoutParams
	var etextParams:		LinearLayout.LayoutParams
	var labelParams:		LinearLayout.LayoutParams
	var layoutParamsX:		LinearLayout.LayoutParams
	var radioParams:		LinearLayout.LayoutParams
	var layoutOrientation:	Int = LinearLayout.HORIZONTAL

	var boxPadding 		= arrayOf(8,16,8,0)
	var spinnerPadding 	= arrayOf(0,8,0,8)

	init{
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			layoutParamsX = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			layoutOrientation = LinearLayout.HORIZONTAL

			labelParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					1.0f
			)

			etextParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					2.0f
			)
			spinnerParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					2.0f
			)
			rgroupParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					2.0f
			)
			radioParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					1.0f
			)
		}else{
			layoutParamsX = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			layoutOrientation = LinearLayout.VERTICAL

			labelParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)

			etextParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			spinnerParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			rgroupParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
			radioParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
		}
	}


}