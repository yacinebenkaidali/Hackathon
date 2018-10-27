package com.example.yacinebenkaidali.habit_tracker;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anis.widget_habbit.CollectionWidget;
import com.example.anis.widget_habbit.R;

import java.util.ArrayList;
import java.util.Arrays;

import DB.Datasrc;
import Donnes.Habit;

public class DataEntryDialog extends DialogFragment  {

     OnCompleteListener mListener;String category;
     String rugular;
    String rugularity[] ={"daily","weekly","twice a week"};
    String Categories[] = {"sport", "reading", "gaming", "working on self","Other"};
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

        final View rootView = inflater.inflate(R.layout.data_entry_dialog,container, false);



        Spinner spinner = rootView.findViewById(R.id.category_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Categories);
        spinner.setAdapter(spinnerArrayAdapter);


        Spinner rug_spinner = rootView.findViewById(R.id.regularity_spinner);
        ArrayAdapter<String> rug_spinner_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, rugularity);
        rug_spinner.setAdapter(rug_spinner_adapter);

        rug_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rugular = rugularity[position] ;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                category =Categories[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                category ="Other";
            }
        });


        final EditText description = rootView.findViewById(R.id.textdescription);
        final RadioGroup radioGroup =  rootView.findViewById(R.id.radiogrp);

        Button btnOk = (Button) rootView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rootView.findViewById(selectedId);
                boolean good_bad = (radioButton.getText().toString()).equals("Bad");
                Habit h = new Habit(description.getText().toString(), good_bad, rugular, category, 1);
                dbsrc.open();
                dbsrc.createHabit(h);
                dbsrc.close();
                mListener.onComplete("hi");
                dismiss();
                // update widget
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.setComponent(new ComponentName(getActivity(), CollectionWidget.class));
                getActivity().sendBroadcast(intent);

            }
        });

        Button btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (getArguments() != null) {
            String desc = getArguments().getString("desc", "");
            boolean CATEGORY = getArguments().getBoolean("cat");
            String REGULARITY= getArguments().getString("reg", "");
            String HABIT_TYPE= getArguments().getString("type", "");
            description.setText(desc);
            String radio ;
            if (CATEGORY==true){
                radioGroup.check(R.id.radio0);
            }else{
                radioGroup.check(R.id.radio1);
            }
            int pos = new ArrayList<String>(Arrays.asList(rugularity)).indexOf(REGULARITY);
            rug_spinner.setSelection(pos);
            pos = new ArrayList<String>(Arrays.asList(Categories)).indexOf(HABIT_TYPE);
            spinner.setSelection(pos);
        }
            return rootView;


        }


    public static interface OnCompleteListener {
        public abstract void onComplete(String time);
    }





}
