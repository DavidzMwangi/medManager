package com.company.mwangidavidwanjohi.medmanager.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = MedManagerDatabase.class)
public class UserProfile extends BaseModel {
    @PrimaryKey
   public int id=1;

    @Column
 public    String name;

    @Column
   public String email;


}
