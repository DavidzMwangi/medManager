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
import com.company.mwangidavidwanjohi.medmanager.adapters.CompletedMedicationAdapter;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class CompletedMedicationFragment extends Fragment {
    RecyclerView recyclerView;
    CompletedMedicationAdapter completedMedicationAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.completed_medication_fragment,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set toolbar title
        getActivity().setTitle("Completed Medication");
       //set the recyclerview for the completed medication
        recyclerView=(RecyclerView)view.findViewById(R.id.completed_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //get the record of the completed medication
        List<Medication> completedMedications= SQLite.select().from(Medication.class)
                        .where(Medication_Table.completed.eq(true)).queryList();
        //set the records to the adapter thn set the adapter to the recyclerview
        completedMedicationAdapter=new CompletedMedicationAdapter(getContext(),completedMedications);
        recyclerView.setAdapter(completedMedicationAdapter);


    }
}
