package com.measurelet.Model;

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
public class Weight {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "patientID")
    @NonNull
    public String patientID;

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @ColumnInfo(name = "Weight")
    public double weight;

    @Ignore
    public Weight(String patientID, double weight ){
        this.id = UUID.randomUUID().toString();
        this.patientID = patientID;
        this.weight = weight;
        this.regDate = new Date();
    }

    public Weight(){ }

}
