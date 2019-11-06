package cl.datageneral.dynamicforms.ui.form.spinner

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import pablo.molina.jsonform.spinner.SelectableItem

class MySpinnerAdapter(
		context: Context,
		textViewResourceId: Int,
		val selectedItemStyle:Int?,
		private val myObjs: List<SelectableItem>) :
			ArrayAdapter<SelectableItem>(
					context,
					textViewResourceId,
					myObjs) {

	override fun getCount(): Int {
		return myObjs?.size ?: 0
	}

	override fun getItem(position: Int): SelectableItem? {
		return myObjs!![position]
	}

	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val label 	= TextView(context)
		label.text 	= myObjs!![position].value
		if(selectedItemStyle!=null) {
			label.setTextAppearance(context, selectedItemStyle)
		}
		//label.setValue(myObjs[position].getValue());
		//return label;
		// COMPATIBILIDAD CON LOLIPOP
		val rtlContainer = RelativeLayout(context)
		rtlContainer.addView(label)
		return rtlContainer
	}

	override fun getDropDownViewTheme(): Resources.Theme? {
		return super.getDropDownViewTheme()
	}

	override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
		val inflater 	= LayoutInflater.from(context)
		val rowView 	= inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
		val label 		= rowView.findViewById<TextView>(android.R.id.text1)
		//TextView label = new TextView(context);
		label.text 		= myObjs!![position].value
		label.post { label.setSingleLine(false) }
		return label
	}


}

