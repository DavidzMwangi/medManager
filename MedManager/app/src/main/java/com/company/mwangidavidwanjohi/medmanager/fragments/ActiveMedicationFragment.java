package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.adapters.ActiveMedicationAdapter;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime_Table;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
        List<Medication> medications= SQLite.select().from(Medication.class).where(Medication_Table.completed.eq(false)).queryList();
        //set the data to the adapter
            myAdapter=new ActiveMedicationAdapter(getContext(),medications);
            recyclerView.setAdapter(myAdapter);
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
