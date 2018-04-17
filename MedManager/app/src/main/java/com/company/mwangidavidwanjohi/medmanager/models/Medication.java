package com.company.mwangidavidwanjohi.medmanager.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.UUID;

@Table(database = MedManagerDatabase.class)
public class Medication extends BaseModel {

//
    @PrimaryKey
     public int id=0;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public  int frequency_in_a_day;

    @Column
    public int amount_per_frequency;

    @Column
    public  String start_date;

    @Column
    public String end_date;

    @Column
    public boolean completed=false;

    @Column
    public int month;

    @Column
    public boolean activate_alarm=true;
}
