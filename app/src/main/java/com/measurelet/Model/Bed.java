package com.measurelet.Model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
