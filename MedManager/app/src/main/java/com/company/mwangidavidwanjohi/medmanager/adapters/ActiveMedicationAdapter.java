package com.company.mwangidavidwanjohi.medmanager.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.HomeActivity;
import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.fragments.ActiveMedicationFragment;
import com.company.mwangidavidwanjohi.medmanager.fragments.CompletedMedicationFragment;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class ActiveMedicationAdapter extends RecyclerView.Adapter<ActiveMedicationAdapter.ViewHolder>  {
        private List<Medication> myDataset;
         Context context;
    public ActiveMedicationAdapter(Context c,List<Medication> dataset){
            myDataset=dataset;
            this.context=c;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_active_medicine_card_view,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
          //loop through the mydataset displaying each in a card view
            final Medication medication=myDataset.get(position);
            holder.medicineName.setText(medication.name);
            holder.description.setText(medication.description);
            String frequncy=medication.amount_per_frequency+" X "+medication.frequency_in_a_day;
            holder.frequency.setText(frequncy);
            String medication_duration=medication.start_date+" To "+medication.end_date;
            holder.duration.setText(medication_duration);
            //determine if the alarm is activated or not
            if (medication.activate_alarm){
                holder.deactivate.setChecked(true);
            }else{
                holder.deactivate.setChecked(false);
            }

            //checkbox to deactivate a medication alarm
        holder.deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medication new_value=myDataset.get(position);

                if (holder.deactivate.isChecked()){
                    holder.alarmStatus.setText("Alarm Enabled");
                    Toast.makeText(context,"Alarm has been enabled for "+ medication.name,Toast.LENGTH_SHORT).show();
                    //change the alarm value in the database to true
                    new_value.activate_alarm=true;
                    new_value.save();
                    //activate alarm in the database
                    ActiveMedicationFragment.activateAlarm(new_value.id);

                }else {
                    holder.alarmStatus.setText("Alarm Disabled");
                    Toast.makeText(context,"Alarm has been disabled for "+medication.name,Toast.LENGTH_SHORT).show();
                    //change the alarm value in the database to false
                    new_value.activate_alarm=false;
                    new_value.save();
                    //deactivate alarm in database
                    ActiveMedicationFragment.deactivate(new_value.id);
                }
            }
        });

            //checkbox to mark medication as completed
        holder.mark_completed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Medication medi=myDataset.get(position);
                //if checkbox is checked, change the value in db to completed
                if (holder.mark_completed.isChecked()){
                    Toast.makeText(context,"Compeleted",Toast.LENGTH_SHORT).show();
                    medi.completed=true;
                    medi.save();

                    //reload the fragment to reflect the new records that are updated
                    ActiveMedicationFragment activeMedicationFragment=new ActiveMedicationFragment();
                    ((HomeActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,activeMedicationFragment).commit();


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }


    public void updateData(List<Medication> updatedList){
        myDataset.clear();

        myDataset=updatedList;

        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView medicineName,description,frequency,duration,alarmStatus;
        CheckBox deactivate,mark_completed;
        public ViewHolder(View itemView) {
            super(itemView);
                medicineName=(TextView)itemView.findViewById(R.id.active_medicine_name);
                description=(TextView)itemView.findViewById(R.id.active_description);
                frequency=(TextView)itemView.findViewById(R.id.frequency);
                duration=(TextView)itemView.findViewById(R.id.active_start_end_date);
                deactivate=(CheckBox) itemView.findViewById(R.id.deactivate_checkbox);
                alarmStatus=(TextView)itemView.findViewById(R.id.alarm_status);
                mark_completed=(CheckBox)itemView.findViewById(R.id.completed_checkbox);

        }
    }


}
