package com.company.mwangidavidwanjohi.medmanager.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.company.mwangidavidwanjohi.medmanager.utils.AlarmTimeController;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmTimeController.cancelAllAlarms(context);
            AlarmTimeController.setNextAlarm(context);
        }
    }
}
