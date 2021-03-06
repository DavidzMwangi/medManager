package com.company.mwangidavidwanjohi.medmanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.searched_medicine_name.setText(medication.name);
        holder.short_description.setText(medication.description);
        String frequency=medication.amount_per_frequency+" X "+medication.frequency_in_a_day;
        holder.searched_frequency.setText(frequency);
        holder.searched_duration.setText(medication.start_date+" To "+medication.end_date);
        if (medication.completed){
            holder.completed.setText("Medication Completed");
            holder.completed.setTextColor(Color.BLUE);
        }else{
            holder.completed.setText("Medication Incomplete");
            holder.completed.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
      return   medications.size();
    }

    public static  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView searched_medicine_name,short_description,completed,searched_frequency,searched_duration;

        public ViewHolder(View itemView) {
            super(itemView);
            searched_medicine_name=(TextView)itemView.findViewById(R.id.searched_medicine_name);
            short_description=(TextView)itemView.findViewById(R.id.short_description);
            completed=(TextView)itemView.findViewById(R.id.completed);
            searched_frequency=(TextView)itemView.findViewById(R.id.searched_frequency);
            searched_duration=(TextView)itemView.findViewById(R.id.searched_duration);
        }
    }

    public  void updateData(List<Medication> medicats){
        medications=medicats;
        this.notifyDataSetChanged();
    }
}
