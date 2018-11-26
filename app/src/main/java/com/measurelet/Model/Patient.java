package com.measurelet.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;


@Entity
public class Patient {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "PatientName")
    public String name;

    @ColumnInfo(name = "RegDate")
    public Date regDate;

    @Ignore
    public Patient(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.regDate = new Date();
    }

    public Patient(){
    }
}
