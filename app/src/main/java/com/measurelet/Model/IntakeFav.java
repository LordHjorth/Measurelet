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


    private UUID id = UUID.randomUUID();
    private int amount;
    private Enums.IntakeType type;
    private Date regDate;

    public IntakeFav(Enums.IntakeType type, int amount){
        this.id = UUID.randomUUID();
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
