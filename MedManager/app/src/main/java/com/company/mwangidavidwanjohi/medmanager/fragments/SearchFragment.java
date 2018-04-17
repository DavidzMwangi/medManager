package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.HomeActivity;
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
        TextView noResults;
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

        noResults=(TextView)view.findViewById(R.id.no_results_tv);
      try {
        String  query = bundle.getString("searched_key_word");
          //query for the records with similar name as the medication name
          Log.e("the_query",query);
          List<Medication> medi= SQLite.select().from(Medication.class)
                  .where(Medication_Table.name.like(query)).queryList();
          // if list is empty display the no results textView
          noResults.setVisibility(View.VISIBLE);

          //set the data to the adapter
          searchAdapter=new SearchAdapter(getContext(),medi);
          recyclerView.setAdapter(searchAdapter);



      }catch (Exception e){
          //incase an error occurs
          Toast.makeText(getContext(),"An Error has occurred. Please try again",Toast.LENGTH_LONG).show();
          Intent intent=new Intent(getActivity(), HomeActivity.class);
          startActivity(intent);
      }


    }

}
