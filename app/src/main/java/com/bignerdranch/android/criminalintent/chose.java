package com.bignerdranch.android.criminalintent;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class chose extends DialogFragment {

    private static String ARG_DATE;
    public static Button dateB;
    public static Button timeB;
    public static final String RESULT="Result";
    public  static int chose;
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.selection,null);
        dateB=(Button) v.findViewById(R.id.date_button);

        timeB=(Button) v.findViewById(R.id.time_button);

        dateB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chose=0;
                        Intent i =new Intent().putExtra(RESULT,chose);
                        getTargetFragment().onActivityResult(getTargetRequestCode(),chose,i);
                    }
                });
        timeB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chose=1;
                        Intent i=new Intent().putExtra(RESULT,chose);
                        getTargetFragment().onActivityResult(getTargetRequestCode(),chose,i);

                    }
                });

               return new AlertDialog.Builder(getActivity()).setView(v).create();

    }
public static void updateButton(Date date){
    dateB.setText("(Date) "+new SimpleDateFormat("EE:MMMM:YYYY").
            format(date));

    timeB.setText("(Time) " +new SimpleDateFormat("HH:mm:ss").
            format(date));


}


    public static chose Dateset(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);


        chose fragment = new chose();
        fragment.setArguments(args);

        return fragment;
    }
}
