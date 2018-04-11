package com.company.mwangidavidwanjohi.medmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
        Context context;
        List<Medication> medications;
    public SearchAdapter(Context c, List<Medication> med){
        context=c;
        medications=med;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_search_card_view,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medication medication=medications.get(position);
        Log.e("data",medication.name);
    }

    @Override
    public int getItemCount() {
      return   medications.size();
    }

    public static  class ViewHolder extends  RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public  void updateData(List<Medication> medicats){
        medications=medicats;
        this.notifyDataSetChanged();
    }
}
