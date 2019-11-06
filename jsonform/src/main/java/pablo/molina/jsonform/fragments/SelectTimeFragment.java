package pablo.molina.jsonform.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	private TextView mTime;

	@SuppressLint("ValidFragment")
	public SelectTimeFragment(TextView element) {
		this.mTime = element;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int hh = calendar.get(Calendar.HOUR);
		int nn = calendar.get(Calendar.MINUTE);
		return new TimePickerDialog(getActivity(), this, hh, nn, false);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hh, int nn) {
		updateDisplay(hh,nn);
	}
	
	private void updateDisplay(int hh, int nn) {
		mTime.setText(
				new StringBuilder()
				.append(pad(hh)).append(":")
				.append(pad(nn)));
	}		
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}