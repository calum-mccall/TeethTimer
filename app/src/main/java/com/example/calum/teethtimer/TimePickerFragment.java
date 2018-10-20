package com.example.calum.teethtimer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
                                implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute =calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, currentHour, currentMinute,true);
    }

    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        SetAlarm setAlarm = new SetAlarm();
        String morningOrEvening = getTag();
        setAlarm.setAlarm(getContext(), hourOfDay, minute, morningOrEvening);

        this.getActivity().finish();
    }
}
