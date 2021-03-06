package xyz.eeckhout.smartcity.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import org.joda.time.DateTime;

import java.util.Calendar;

import xyz.eeckhout.smartcity.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private EditText birthdate;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        birthdate = (EditText) getActivity().findViewById(R.id.register_birthdate);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog , this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthdate.setTag(new DateTime(year, month + 1,  dayOfMonth, 0, 0));
        birthdate.setText(dayOfMonth+"/"+month + 1+"/"+year);
    }
}