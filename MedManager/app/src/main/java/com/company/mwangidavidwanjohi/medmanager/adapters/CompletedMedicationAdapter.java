package com.company.mwangidavidwanjohi.medmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.company.mwangidavidwanjohi.medmanager.HomeActivity;
import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.fragments.CompletedMedicationFragment;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CompletedMedicationAdapter  extends RecyclerView.Adapter<CompletedMedicationAdapter.ViewHolder> {
    List<Medication> myData;
    Context context;
    public CompletedMedicationAdapter(Context c,List<Medication> dataset){
        myData=dataset;
        context=c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_completed_medicine_card_view,parent,false);
        return  new ViewHolder(item);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //loop through the myData displaying each in the card view
        final  Medication med=myData.get(position);
        holder.med_name.setText(med.name);
        holder.short_description.setText(med.description);
        String duration=med.start_date+ " To "+med.end_date;
        holder.completed_duration.setText(duration);
        String frequncy=med.amount_per_frequency+" X "+med.frequency_in_a_day;
        holder.completed_frequency.setText(frequncy);

        //if the end date has reached, hide the checkbox
        try {
            Date end_date=new SimpleDateFormat("dd-MM-yyyy").parse(med.end_date);


            Date today_date = Calendar.getInstance().getTime();
            if (end_date.before(today_date)){
//                Log.e("working","Its working");
//                Log.e("med_name",med.name);
                holder.incomplete.setVisibility(View.INVISIBLE);
                holder.mark_completed.setVisibility(View.INVISIBLE);
            }


        } catch (ParseException e) {
            Log.e("error",e.toString());
        }



        holder.incomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // if the checkbox is checked
                if (holder.incomplete.isChecked()) {
                    //change the value of completed to false
                    SQLite.update(Medication.class).set(Medication_Table.completed.eq(false))
                            .where(Medication_Table.id.eq(med.id)).executeUpdateDelete();

                    //refresh the fragment to display the new data
                    CompletedMedicationFragment completedMedicationFragment = new CompletedMedicationFragment();
                    ((HomeActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frame, completedMedicationFragment).commit();

                }
            }
        });

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
        TextView med_name,short_description,completed_duration,completed_frequency,mark_completed;
        CheckBox incomplete;
        public ViewHolder(View itemView) {
            super(itemView);
            med_name=(TextView)itemView.findViewById(R.id.completed_medicine_name);
            short_description=(TextView)itemView.findViewById(R.id.short_description);
            completed_duration=(TextView)itemView.findViewById(R.id.completed_duration);
            completed_frequency=(TextView)itemView.findViewById(R.id.completed_frequency);
            incomplete=(CheckBox)itemView.findViewById(R.id.incomplete_checkbox);
            mark_completed=(TextView)itemView.findViewById(R.id.mark_completed);
        }
    }
}
