package com.measurelet.Model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Patient {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "PatientName")
    public String patientName;

    @ColumnInfo(name = "RegDate")
    public Date regDate;

    @Ignore
    public Patient(String patientName){
        this.id = UUID.randomUUID().toString();
        this.patientName = patientName;
        this.regDate = new Date();
    }

    public Patient(){
    }
}
