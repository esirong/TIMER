package com.esirong.timer.activity;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;

import com.esirong.timer.R;
import com.esirong.timer.view.DateTimePickerDialog;
import com.esirong.timer.view.DateTimePickerDialog.OnDateTimeSetListener;

public class TimePickerFragment {

	public static DateTimePickerDialog init(Context cxt) {
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(cxt,
				cal.getTimeInMillis());
		timePicker.setTitle("开始时间");
		timePicker.setIcon(R.drawable.time_flag);
		timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

			@Override
			public void OnDateTimeSet(AlertDialog dialog, long date) {

			}
		});
		return timePicker;
	}

}
