package com.measurelet.Model;

import com.measurelet.Enums;

import java.util.Date;
import java.util.UUID;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"),
        indices = {@Index("patientID")})
public class Intake {

    @PrimaryKey
    @NonNull
    public UUID id = UUID.randomUUID();

    @ColumnInfo(name = "patientID")
    @NonNull
    public UUID patientID = UUID.randomUUID();

    @ColumnInfo(name = "Amount")
    public int amount;

    @ColumnInfo(name = "Type")
    public Enums.IntakeType type;

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @Ignore
    public Intake(@NonNull UUID patientID, Enums.IntakeType type, int amount){
        this.id = UUID.randomUUID();
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

    public Intake(){ }

}
