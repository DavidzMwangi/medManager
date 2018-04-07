package com.company.mwangidavidwanjohi.medmanager.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.UUID;

@Table(database = MedManagerDatabase.class)
public class Medication extends BaseModel {

    @PrimaryKey
    int id;

    @Column
    String name;

    @Column
    String description;

    @Column
    int frequency_in_a_day;

    @Column
    int amount_per_frequency;

    @Column
    String start_date;

    @Column
    String end_date;

    @Column
    boolean completed=false;

    @Column
    boolean activate_alarm=true;
}
