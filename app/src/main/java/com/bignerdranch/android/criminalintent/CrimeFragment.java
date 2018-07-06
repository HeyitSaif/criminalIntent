package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.OnCheckedChangeListener;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    public static final String DIALOG_DATE = "DialogDate";
    public static final String BOOL = "boolean data";

    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_REQUEST="REQUEST";
    private static final int REQUEST_INPUT=2;
    private static final int REQUEST_DATE = 0;
    private static boolean CHECK = false;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_crime,menu);


}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_remove:
                String s;
                if(mCrime.getTitle()==null)
                  s="Selected Crime";
                    else
                s=mCrime.getTitle();
               Toast tost= Toast.makeText(getContext(),s+" has been successfully deleted", Toast.LENGTH_SHORT);
                TextView v=(TextView) tost.getView().findViewById(android.R.id.message);
                if (v!=null){v.setGravity(Gravity.CENTER);v.setTypeface(null, Typeface.ITALIC);}
                tost.show();
                CrimeLab.get(getActivity()).dCrime(mCrime);



                getActivity().finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                getActivity().setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
               chose c=new chose().Dateset(mCrime.getDate());

               c.setTargetFragment(CrimeFragment.this,REQUEST_INPUT);
               c.show(manager,DIALOG_REQUEST);

            }
        });

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode==REQUEST_INPUT) {
           FragmentManager manager = getFragmentManager();





           if (resultCode==1) {
            CHECK=false;
           }
           else if (resultCode==0){

               CHECK=true;

           }
           DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate(),CHECK);
           dialog.setTargetFragment(CrimeFragment.this, REQUEST_INPUT);
           dialog.show(manager,DIALOG_TIME);
       }
        if (requestCode == REQUEST_DATE) {

            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);

            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
        
    }
}
