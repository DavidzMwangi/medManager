package com.company.mwangidavidwanjohi.medmanager.utils;

import com.company.mwangidavidwanjohi.medmanager.models.AlarmTime;

import java.sql.Time;
import java.util.Calendar;
import java.util.Random;

public class AlarmTimeController {

    public static void alarmCreater(int primary_key,int frequency_in_a_day){
       //the method saves the frequency by which the medicine should be taken
        switch (frequency_in_a_day){
            case 1:{
                /*
                int twenty= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int twelve=Calendar.getInstance().get(Calendar.HOUR);
        Log.e("time_in_12", String.valueOf(twenty+" " + twelve));
                 */

                AlarmTime alarmTime=new AlarmTime();
                alarmTime.id=(int)(Math.random()*500+1);
                alarmTime.medicationId=primary_key;
                alarmTime.timeToAlarm=6;
                alarmTime.alarm_enabled=true;
                alarmTime.save();
            }
            case 2:{
                for (int a=1;a<=2;a++){
                    AlarmTime alarmTime=new AlarmTime();
                    alarmTime.id=(int) (Math.random()*500+1);
                    alarmTime.medicationId=primary_key;
                    alarmTime.alarm_enabled=true;

                    if (a==1){
                        alarmTime.timeToAlarm=6;
                        }else {
                        alarmTime.timeToAlarm = 12;
                    }
                    alarmTime.save();
                }

            }
            case 3:{
                for (int a=1;a<=3;a++){
                    AlarmTime alarmTime=new AlarmTime();
                    alarmTime.id=(int) (Math.random()*500+1);
                    alarmTime.medicationId=primary_key;
                    alarmTime.alarm_enabled=true;

                    if (a==1){
                        alarmTime.timeToAlarm=6;
                    }else if (a==2){
                        alarmTime.timeToAlarm = 14;
                    }else{
                        alarmTime.timeToAlarm = 22;

                    }
                    alarmTime.save();
                }
            }
            case 4:{
                for (int a=1;a<=4;a++){
                    AlarmTime alarmTime=new AlarmTime();
                    alarmTime.id=(int) (Math.random()*500+1);
                    alarmTime.medicationId=primary_key;
                    alarmTime.alarm_enabled=true;

                    if (a==1){
                        alarmTime.timeToAlarm=6;
                    }else if (a==2){
                        alarmTime.timeToAlarm = 12;
                    }else if (a==3) {
                        alarmTime.timeToAlarm = 18;

                    }
                    else
                    {
                        alarmTime.timeToAlarm = 0;
                        }
                    alarmTime.save();
                }
            }

            case 6:{

                for (int a=1;a<=6;a++){
                    AlarmTime alarmTime=new AlarmTime();
                    alarmTime.id=(int) (Math.random()*500+1);
                    alarmTime.medicationId=primary_key;
                    alarmTime.alarm_enabled=true;

                    if (a==1){
                        alarmTime.timeToAlarm=0;
                    }else if (a==2){
                        alarmTime.timeToAlarm = 4;
                    }else if (a==3) {
                        alarmTime.timeToAlarm = 8;

                    }else if(a==4){
                        alarmTime.timeToAlarm = 12;

                    }else if(a==5){
                        alarmTime.timeToAlarm = 16;

                    }
                    else
                    {
                        alarmTime.timeToAlarm = 20;
                    }
                    alarmTime.save();
                }
            }

            case 8:{
                for (int a=1;a<=8;a++){
                    AlarmTime alarmTime=new AlarmTime();
                    alarmTime.id=(int) (Math.random()*500+1);
                    alarmTime.medicationId=primary_key;
                    alarmTime.alarm_enabled=true;

                    if (a==1){
                        alarmTime.timeToAlarm=0;
                    }else if (a==2){
                        alarmTime.timeToAlarm = 3;
                    }else if (a==3) {
                        alarmTime.timeToAlarm = 6;

                    }else if(a==4){
                        alarmTime.timeToAlarm = 9;

                    }else if(a==5){
                        alarmTime.timeToAlarm = 12;

                    }else if(a==6){
                        alarmTime.timeToAlarm = 15;

                    }else if(a==7){
                        alarmTime.timeToAlarm = 18;

                    }
                    else
                    {
                        alarmTime.timeToAlarm = 21;
                    }
                    alarmTime.save();
                }
            }

            case 12:{
                for (int a=1;a<=12;a++){
                    AlarmTime alarmTime=new AlarmTime();
                    alarmTime.id=(int) (Math.random()*500+1);
                    alarmTime.medicationId=primary_key;
                    alarmTime.alarm_enabled=true;

                    if (a==1){
                        alarmTime.timeToAlarm=0;
                    }else if (a==2){
                        alarmTime.timeToAlarm = 2;
                    }else if (a==3) {
                        alarmTime.timeToAlarm = 4;

                    }else if(a==4){
                        alarmTime.timeToAlarm = 6;

                    }else if(a==5){
                        alarmTime.timeToAlarm = 8;

                    }else if(a==6){
                        alarmTime.timeToAlarm = 10;

                    }else if(a==7){
                        alarmTime.timeToAlarm = 12;

                    }else if(a==8){
                        alarmTime.timeToAlarm = 14;

                    }else if(a==9){
                        alarmTime.timeToAlarm = 16;

                    }else if(a==10){
                        alarmTime.timeToAlarm = 18;

                    }else if(a==11){
                        alarmTime.timeToAlarm = 20;

                    }
                    else
                    {
                        alarmTime.timeToAlarm = 22;
                    }
                    alarmTime.save();
                }
            }

        }
    }
}
