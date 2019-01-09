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

public class Intake {


    public UUID intakeID;
    String type;
    int size;
    Date timestamp;

    public Intake(String type, int size){
        this.intakeID = UUID.randomUUID();
        this.type = type;
        this.size = size;
        this.timestamp = new Date();
    }

    public Intake(){

    }

}
