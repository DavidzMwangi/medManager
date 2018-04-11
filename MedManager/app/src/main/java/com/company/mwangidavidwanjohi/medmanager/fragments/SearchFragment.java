package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.adapters.SearchAdapter;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class SearchFragment extends Fragment {
        RecyclerView recyclerView;
        SearchAdapter searchAdapter;
        List<Medication> medicationers;
//        public SearchFragment(List<Medication> a){
//            medicationers=a;
//        }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set title for the fragment
        getActivity().setTitle("Search Medicine");
        recyclerView=(RecyclerView)view.findViewById(R.id.search_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Bundle bundle=this.getArguments();
      String query=  bundle.getString("searched_key_word");

        //query for the records with similar name as the medication name
        assert query != null;

//        Log.e("assert",query);
        List<Medication> medi= SQLite.select().from(Medication.class)
                .where(Medication_Table.name.like(query)).queryList();
        //set the data to the adapter
        searchAdapter=new SearchAdapter(getContext(),medi);
        recyclerView.setAdapter(searchAdapter);


    }

}
