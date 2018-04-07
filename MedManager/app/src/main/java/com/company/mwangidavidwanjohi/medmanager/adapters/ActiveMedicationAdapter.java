package com.company.mwangidavidwanjohi.medmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;

import java.util.List;

public class ActiveMedicationAdapter extends RecyclerView.Adapter<ActiveMedicationAdapter.ViewHolder>  {
        private List<Medication> myDataset;
    public ActiveMedicationAdapter(List<Medication> dataset){
            myDataset=dataset;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_active_medicine_card_view,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          //loop through the mydataset displaying each in a card view
            Medication medication=myDataset.get(position);
            holder.medicineName.setText(medication.name);


    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }
        public void updateData(List<Medication> updatedList){
        myDataset=updatedList;
        this.notifyDataSetChanged();
        }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView medicineName;
        public ViewHolder(View itemView) {
            super(itemView);
                medicineName=(TextView)itemView.findViewById(R.id.active_medicine_name);
        }
    }


}
