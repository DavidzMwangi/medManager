package com.company.mwangidavidwanjohi.medmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;

import java.util.List;

public class CompletedMedicationAdapter  extends RecyclerView.Adapter<CompletedMedicationAdapter.ViewHolder> {
    List<Medication> myData;
    public CompletedMedicationAdapter(List<Medication> dataset){
        myData=dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_completed_medicine_card_view,parent,false);
        return  new ViewHolder(item);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //loop through the myData displaying each in the card view
        Medication med=myData.get(position);
        holder.med_name.setText(med.name);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }
    public void updateData(List<Medication> updatedData){
        myData=updatedData;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView med_name;
        public ViewHolder(View itemView) {
            super(itemView);
            med_name=(TextView)itemView.findViewById(R.id.completed_medicine_name);
        }
    }
}
