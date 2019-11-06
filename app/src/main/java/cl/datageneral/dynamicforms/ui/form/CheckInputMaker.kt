package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.content.res.Configuration
import android.widget.*
import pablo.molina.jsonform.spinner.SelectableItem

/**
 * Created by Pablo Molina on 11-10-2019. s.pablo.molina@gmail.com
 */
class CheckInputMaker(val label:String?, val  items:ArrayList<SelectableItem>, val context: Context):BaseMaker(context) {
	var baseLayout = LinearLayout(context)
	var labelObj: TextView? = null
	var group: LinearLayout? = null

	fun getObject(): LinearLayout {
		pLayout()
		pBox()
		pLabel()
		pGroup()
		for (item: SelectableItem in items) {
			pRadioButton(item)
		}
		join()
		return baseLayout
	}

	private fun pLayout() {
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
		} else {
			labelParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
			rgroupParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f)

			layoutOrientation = LinearLayout.HORIZONTAL

			radioParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
			)
		}
	}

	private fun pBox() {
		baseLayout.run {
			// Cambiar por with{}
			orientation = layoutOrientation
			layoutParams = layoutParamsX
			setPadding(
					boxPadding[0],
					boxPadding[1],
					boxPadding[2],
					boxPadding[3])
			//setBackgroundResource(R.color.iconOrange) // Temporal
		}
	}

	private fun pLabel() {
		if (label != null) {
			val t = LabelMaker(label, context)
			t.setLayoutParams(labelParams)
			labelObj = t.getObject()
			//labelObj!!.setBackgroundResource(R.color.iconRed) // Temporal
		}
	}

	private fun pGroup() {
		group = LinearLayout(context)
		group?.run {
			layoutParams = rgroupParams
			//setBackgroundResource(R.color.iconGreen) // Temporal
			orientation = RadioGroup.HORIZONTAL
		}
	}

	private fun pRadioButton(item: SelectableItem) {
		val radioInput = CheckBox(context)
		radioInput.text = item.value
		radioInput.tag = item.id
		radioInput.run {
			layoutParams = radioParams
		}
		//radioInput.setBackgroundResource(R.color.colorGrey_1) // Temporal
		group?.addView(radioInput)
	}

	private fun join() {
		if (labelObj != null) {
			baseLayout.addView(labelObj)
		}

		if (group != null) {
			baseLayout.addView(group)
		}
	}
}