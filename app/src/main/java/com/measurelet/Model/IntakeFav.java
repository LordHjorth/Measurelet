package com.measurelet.Model;

import com.measurelet.Enums;
import com.measurelet.Factories.IntakeFactory;

import java.util.Date;
import java.util.UUID;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


public class IntakeFav {


    public UUID id = UUID.randomUUID();


    public UUID patientID = UUID.randomUUID();

    public int amount;

    public Enums.IntakeType type;

    public Date regDate;

    public IntakeFav(UUID patientID, Enums.IntakeType type, int amount){
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

    public IntakeFav(){ }
}
