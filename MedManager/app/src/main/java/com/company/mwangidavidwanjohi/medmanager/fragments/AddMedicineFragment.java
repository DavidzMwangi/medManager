package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.R;

import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.UserProfile;
import com.company.mwangidavidwanjohi.medmanager.utils.AlarmTimeController;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        //set toolbar title
        getActivity().setTitle("Add Medicine");
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

                Calendar cal=Calendar.getInstance();
                int month=cal.get(Calendar.MONTH);
                if (medicineName.getText().toString().matches("") ||medicineDescription.getText().toString().matches("")){
                    Toast.makeText(getActivity(),"Medicine Name or description cannot be empty",Toast.LENGTH_LONG).show();
                    return;

                }

                //save the record in the database
                Medication med=new Medication();
                med.id = (int)(Math.random()*500+1);
                med.name=medicineName.getText().toString();
                med.description=medicineDescription.getText().toString();
                med.frequency_in_a_day=Integer.parseInt(frequencySpinner.getSelectedItem().toString());
                med.amount_per_frequency=Integer.parseInt(amountSpinner.getSelectedItem().toString());
                med.start_date=startDateStr;
                med.end_date=endDateStr;
                med.completed=false;
                med.month=month;
                med.activate_alarm=true;
                med.save();

                //save the medication frequency of being taken in alarm_time table
                AlarmTimeController.alarmCreater(med.id,med.frequency_in_a_day);

                //direct to the active medication fragment
                ActiveMedicationFragment activeMedicationFragment=new ActiveMedicationFragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,activeMedicationFragment);
                transaction.commit();

            }
        });

        //handle the searching of medicine here
        final AppCompatEditText medicine_query=(AppCompatEditText)view.findViewById(R.id.search_word);
        ImageView search_button=(ImageView) view.findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medicine_query.getText().toString().isEmpty() ){
                    //empty edit text
                    Toast.makeText(getContext(),"You cannot search an empty medicine",Toast.LENGTH_LONG).show();
                }else{

                    //get the medicine name entered
                    String search_value=medicine_query.getText().toString();

                    //open the search fragment and display the data
                    SearchFragment searchFragment=new SearchFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("searched_key_word",search_value);
                    searchFragment.setArguments(bundle);
                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame,searchFragment);
                    transaction.commit();


//

                }
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
       for (int i=1;i<=12;i++){
           //if its 5,7,9,10,11 do not add as there cannot be such a frequency
           if (i==5 || i==7 || i==9 || i==10 || i==11){
               continue;
           }
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
