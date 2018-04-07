package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.R;

import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddMedicineFragment extends Fragment {

     AppCompatEditText medicineName,medicineDescription;
     Spinner amountSpinner;
           Spinner  frequencySpinner;
     DatePicker startDate,endDate;
     AppCompatButton save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_medicine,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FlowManager.init(getActivity());
        medicineName=(AppCompatEditText)view.findViewById(R.id.medicine_name);
        medicineDescription=(AppCompatEditText)view.findViewById(R.id.medicine_description);
        amountSpinner=(Spinner)view.findViewById(R.id.amount_spinner);
        frequencySpinner=(Spinner)view.findViewById(R.id.frequency_spinner);
        startDate=(DatePicker)view.findViewById(R.id.start_date);
        startDate.setMinDate(System.currentTimeMillis() - 1000);

        endDate=(DatePicker)view.findViewById(R.id.end_date);
        endDate.setMinDate(System.currentTimeMillis() - 1000);

        save=(AppCompatButton)view.findViewById(R.id.save);


        //set the data to appear on frequency spinner
        addFrequencySpinner(view);
        //set the data to appear on the amount spinner
        addAmountSpinner(view);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //start date
               String startDateStr= dateConverter(startDate.getDayOfMonth(),startDate.getMonth(),startDate.getYear());
                //end date
                String endDateStr=dateConverter(endDate.getDayOfMonth(),endDate.getMonth(),endDate.getYear());

                if (medicineName.getText().toString().matches("") ||medicineDescription.getText().toString().matches("")){
                    Toast.makeText(getActivity(),"Medicine Name or description cannot be empty",Toast.LENGTH_LONG).show();
                    return;

                }

                //save the record in the database
                Medication med=new Medication();
                med.id = Math.random();
                med.name=medicineName.getText().toString();
                med.description=medicineDescription.getText().toString();
                med.frequency_in_a_day=frequencySpinner.getSelectedItem();
                med.amount_per_frequency=amountSpinner.getSelectedItem();
                med.start_date=startDateStr;
                med.end_date=endDateStr;
                med.completed=false;
                med.activate_alarm=true;
                med.save();

                                Toast.makeText(getActivity(),frequencySpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();


            }
        });
    }

    public String dateConverter(int day,int month, int year){
        SimpleDateFormat dateFormatter=new SimpleDateFormat("dd-MM-yyyy");
        Date d=new Date(year-1900,month,day);
        return dateFormatter.format(d);

    }
    public void addFrequencySpinner(View view){
        frequencySpinner=(Spinner)view.findViewById(R.id.frequency_spinner);
        List<Integer> freq=new ArrayList<Integer>();
       for (int i=1;i<=24;i++){
           freq.add(i);
       }
        ArrayAdapter<Integer> dataAdapter=new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item,freq);
       dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       frequencySpinner.setAdapter(dataAdapter);


    }
    public void addAmountSpinner(View view){
        amountSpinner=(Spinner)view.findViewById(R.id.amount_spinner);
        List<Integer> amount=new ArrayList<Integer>();
        for (int i=1;i<=10;i++){
            amount.add(i);
        }
        ArrayAdapter<Integer> amountAdapter=new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item,amount);
        amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountSpinner.setAdapter(amountAdapter);
    }
}
