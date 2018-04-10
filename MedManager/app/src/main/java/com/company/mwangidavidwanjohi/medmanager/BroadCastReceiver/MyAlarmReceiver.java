package com.company.mwangidavidwanjohi.medmanager.BroadCastReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.company.mwangidavidwanjohi.medmanager.HomeActivity;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime;
import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime_Table;
import com.company.mwangidavidwanjohi.medmanager.models.Medication;
import com.company.mwangidavidwanjohi.medmanager.models.Medication_Table;
import com.company.mwangidavidwanjohi.medmanager.utils.AlarmScheduler;
import com.company.mwangidavidwanjohi.medmanager.utils.AlarmTimeController;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.List;

public class MyAlarmReceiver extends BroadcastReceiver {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Test","Alarm received");

        int current_hour= Calendar.HOUR_OF_DAY + 1;
        List<AlarmTime> current_alarm_times= SQLite.select().from(AlarmTime.class)
                .where(AlarmTime_Table.alarm_enabled.eq(true))
                .and(AlarmTime_Table.timeToAlarm.eq(current_hour)).queryList();

        if(current_alarm_times.size()>0){
            StringBuilder sb=new StringBuilder();
            for (AlarmTime a_t:current_alarm_times){
                Log.e("Alarm_time",""+a_t.medicationId);
                Medication current = SQLite.select().from(Medication.class).where(Medication_Table.id.eq(a_t.medicationId)).querySingle();
                sb.append(current.name).append(",");
            }
            AlarmScheduler.showNotification(context, HomeActivity.class,
                    "You have Medication not taken", "Take " + sb.toString() + " Now");
        }else{
            Log.e("Alarm","No current med times tobe fired");
        }

        AlarmTimeController.setNextAlarm(context);
    }
}
