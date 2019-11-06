package pablo.molina.jsonform.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	private EditText mEdit;
	
	public SelectDateFragment(EditText element){
		this.mEdit = element;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int yy = calendar.get(Calendar.YEAR);
		int mm = calendar.get(Calendar.MONTH);
		int dd = calendar.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	}	
	@Override
	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
		populateSetDate(yy, mm+1, dd);
	}
	
	public void populateSetDate(int year, int month, int day) {
		//mEdit = (EditText)findViewById(R.id.fecha);
		mEdit.setText(day+"/"+month+"/"+year);
	}
}