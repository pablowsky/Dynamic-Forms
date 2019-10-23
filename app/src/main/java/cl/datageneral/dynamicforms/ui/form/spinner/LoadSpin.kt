package cl.datageneral.dynamicforms.ui.form.spinner

import android.R
import android.content.Context
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner

import java.util.ArrayList

/**
 * VERSION CLASS: 1.7, FECHA 16-04-2018
 */
class LoadSpin(private val context: Context, private val spinner: Spinner) {
	private var items: 				ArrayList<SelectableItem> = ArrayList()
	private var selected: 			Int = 0
	private var onItemListener: 	OnItemSelectedListener? = null
	private var FirstItem: 			SelectableItem? = null
	private val includeFirstRow: 	Boolean
	var selectedItemStyle: 	Int? = null

	init {
		this.selected = 0
		this.onItemListener = null
		this.includeFirstRow = false
	}

	fun setData(data: ArrayList<SelectableItem>) {
		this.items = data
	}

	fun includeFirstRow(value: SelectableItem) {
		this.FirstItem = value
	}

	fun setSelected(id: Int) {
		this.selected = id
	}

	fun setListener(onitemlistener: OnItemSelectedListener) {
		this.onItemListener = onitemlistener
	}

	fun Load() {
		if (FirstItem != null) {
			items!!.add(0, FirstItem!!)
		}

		val adapter2 = MySpinnerAdapter(
				this.context,
				R.layout.simple_list_item_1,
				selectedItemStyle,
				items)
		spinner.adapter = adapter2
		loadListener()
		spinner.setSelection(getIndex(spinner, selected))
	}

	private fun getCnt(cnt: Int): Int {
		val r: Int
		if (includeFirstRow) {
			r = cnt + 1
		} else
			r = cnt
		return r
	}

	fun loadListener() {
		if (onItemListener != null)
			spinner.onItemSelectedListener = onItemListener
	}

	private fun getIndex(spinner: Spinner, id: Int): Int {
		var index = 0
		var i = 0
		while (i < spinner.count) {
			val obj = spinner.getItemAtPosition(i) as SelectableItem
			//Log.d("Obj:", Integer.toString(obj.getId()));
			if (obj.id == id) {
				//Log.d("Obj2:", Integer.toString(myString));
				index = i
				i = spinner.count
			}
			i++
		}
		return index
	}
}
