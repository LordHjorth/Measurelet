package com.measurelet.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;


@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"),
        indices = {@Index("patientID")})
public class Bed {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "patientID")
    @NonNull
    public String patientID;

    @ColumnInfo(name = "BedNum")
    public int bedNum;

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @Ignore
    public Bed(String patientID, int bedNum){
        this.id = UUID.randomUUID().toString();
        this.patientID = patientID;
        this.bedNum = bedNum;
        this.regDate = new Date();
    }

    public Bed(){

    }

}
