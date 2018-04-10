package com.company.mwangidavidwanjohi.medmanager.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.company.mwangidavidwanjohi.medmanager.HomeActivity;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime_Table;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.company.mwangidavidwanjohi.medmanager.utils.AlarmScheduler;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null && context!=null){
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
                //set the alarm here
                AlarmScheduler.setReminder(context,AlarmReceiver.class);

                return;
            }
        }
        int current_hour= Calendar.HOUR_OF_DAY;
        List<AlarmTime> current_alarm_times=SQLite.select().from(AlarmTime.class)
                .where(AlarmTime_Table.alarm_enabled.eq(true))
                .and(AlarmTime_Table.timeToAlarm.eq(current_hour)).queryList();
        //Trigger the notification
        //loop through the medications displaying them in the notification if its not empty
        if (current_alarm_times.size()==0){

        }else {
            for (AlarmTime alarmTime : current_alarm_times) {

                //get the medication from the table
                Medication current = SQLite.select().from(Medication.class).where(Medication_Table.id.eq(alarmTime.medicationId)).querySingle();
                AlarmScheduler.showNotification(context, HomeActivity.class,
                        "You have Medication not taken", "Take " + current.name + " Now");

            }
        }
    }
}
