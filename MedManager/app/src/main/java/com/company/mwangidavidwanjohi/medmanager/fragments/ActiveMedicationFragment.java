package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.adapters.ActiveMedicationAdapter;
import com.company.mwangidavidwanjohi.medmanager.adapters.SearchAdapter;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime_Table;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActiveMedicationFragment extends Fragment {
    RecyclerView recyclerView;
ActiveMedicationAdapter myAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.active_medication_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set the toolbar title here
        getActivity().setTitle("Active Medication");
        //set the recycler view for the active medication
        recyclerView=view.findViewById(R.id.active_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //get the active medication from the database
        List<Medication> medications= SQLite.select().from(Medication.class)
                .where(Medication_Table.completed.eq(false))
                .orderBy(Medication_Table.month,true)
                .queryList();
        //set the data to the adapter
            myAdapter=new ActiveMedicationAdapter(getContext(),medications);
            recyclerView.setAdapter(myAdapter);

            //handle the search medicine part
        final AppCompatEditText medicine_query=(AppCompatEditText)view.findViewById(R.id.medicine_query);
        ImageView search_medicine_img=(ImageView) view.findViewById(R.id.search_medicine_button);

        search_medicine_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medicine_query.getText().toString().isEmpty() ){
                    //empty edit text
                    Toast.makeText(getContext(),"You cannot search an empty medicine",Toast.LENGTH_LONG).show();
                }else{

                    //query db for such a product
                    String search_value=medicine_query.getText().toString();
                    List<Medication> medics=SQLite.select().from(Medication.class)
                                    .where(Medication_Table.name.like(search_value)).queryList();
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


    public static void activateAlarm(int medication_id){
        SQLite.update(AlarmTime.class)
                .set(AlarmTime_Table.alarm_enabled.eq(true))
                .where(AlarmTime_Table.medicationId.eq(medication_id))
                .executeUpdateDelete();
    }

    public static void deactivate(int medication_id){
        SQLite.update(AlarmTime.class)
                .set(AlarmTime_Table.alarm_enabled.eq(false))
                .where(AlarmTime_Table.medicationId.eq(medication_id))
                .executeUpdateDelete();
    }
}
