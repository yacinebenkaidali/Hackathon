package com.example.yacinebenkaidali.habit_tracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import DB.Datasrc;
import Donnes.Habit;

public class DataEntryDialog extends DialogFragment {

     OnCompleteListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Activity act =(Activity)context;
            this.mListener = (OnCompleteListener) act;
        } catch (final ClassCastException e) {
            throw new ClassCastException(" must implement OnCompleteListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Datasrc dbsrc = new Datasrc(getActivity());
        View rootView = inflater.inflate(R.layout.data_entry_dialog,
                container, false);
        String Categories[] = {"sport", "reading", "gaming", "working on self"};

        Spinner spinner = rootView.findViewById(R.id.category_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Categories);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        final EditText description = rootView.findViewById(R.id.textdescription);
        final EditText textcat = rootView.findViewById(R.id.textcat);


        Button btnOk = (Button) rootView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean category = Boolean.parseBoolean(textcat.getText().toString());
                Habit h = new Habit(description.getText().toString(), category, "", "", 1);
                dbsrc.open();
                dbsrc.createHabit(h);
                dbsrc.close();
                mListener.onComplete("hi");
                dismiss();
            }
        });

        Button btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }





}
