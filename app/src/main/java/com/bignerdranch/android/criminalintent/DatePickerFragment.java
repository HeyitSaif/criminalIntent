package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "com.bignerdranch.android.criminalintent.date";

    private static final String ARG_DATE = "date";

    public static final String BOOL = "boolean data";

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    public static DatePickerFragment newInstance(Date date,boolean CHECK) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        args.putSerializable(BOOL,CHECK);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour=calendar.get(Calendar.HOUR);
        final int minute=calendar.get(Calendar.MINUTE);

        View v;
        if(((boolean)getArguments().getSerializable(BOOL))==true){

         v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
            mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
            mDatePicker.init(year, month, day, null);

        }
                else  {
            v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.dialog_time, null);
        mTimePicker=(TimePicker) v.findViewById(R.id.time_dialog);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        }



        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time and date of Crime")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {


                                if(((boolean)getArguments().getSerializable(BOOL))==false) {

                                    int minutes=mTimePicker.getCurrentMinute();
                                    int hours=mTimePicker.getCurrentHour();

                                Date date = new GregorianCalendar(year, month, day,hours,minutes).getTime();
                                sendResult(Activity.RESULT_OK, date);}



                                else { int year = mDatePicker.getYear();
                                   int month = mDatePicker.getMonth();
                                   int day = mDatePicker.getDayOfMonth();
                                   Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);}
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

    getTargetFragment()
            .onActivityResult(0, resultCode, intent);
    }
}
