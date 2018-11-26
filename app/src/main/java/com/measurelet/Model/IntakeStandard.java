package com.measurelet.Model;


import com.measurelet.Enums;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"))
public class IntakeStandard {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "patientID")
    @NonNull
    public String patientID;

    @ColumnInfo(name = "Amount")
    public long amount;

    @ColumnInfo(name = "Type")
    public Enums.IntakeType type;

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @Ignore
    public IntakeStandard(String patientID, Enums.IntakeType type, int amount){
        this.id = UUID.randomUUID().toString();
        this.patientID = patientID;
        this.type = type;
        this.regDate = new Date();

        if(type != Enums.IntakeType.Custom){
            this.amount = type.amount;
        }
        else {
            this.amount = amount;
        }
    }

}
