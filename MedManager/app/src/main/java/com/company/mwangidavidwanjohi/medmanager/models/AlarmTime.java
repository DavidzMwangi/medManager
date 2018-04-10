package com.company.mwangidavidwanjohi.medmanager.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = MedManagerDatabase.class)
public class AlarmTime extends BaseModel {
    @PrimaryKey
    public int id;

    @Column
    public int medicationId;

    @Column
    public int timeToAlarm;

    @Column
    public boolean alarm_enabled;
}
