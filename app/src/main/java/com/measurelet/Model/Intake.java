package com.measurelet.Model;

import com.measurelet.Enums;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"))
public class Intake {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "patientID")
    @NonNull
    public String patientID;

    @ColumnInfo(name = "Amount")
    public int amount;

    @ColumnInfo(name = "Type")
    public Enums.IntakeType type;

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @Ignore
    public Intake(String patientID, Enums.IntakeType type, int amount){
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
