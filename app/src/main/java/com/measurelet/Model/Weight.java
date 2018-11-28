package com.measurelet.Model;

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
public class Weight {

    @PrimaryKey
    @NonNull
    public UUID id = UUID.randomUUID();

    @ColumnInfo(name = "patientID")
    @NonNull
    public UUID patientID = UUID.randomUUID();

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @ColumnInfo(name = "Weight")
    public double weight;

    @Ignore
    public Weight(@NonNull UUID patientID, double weight ){
        this.id = UUID.randomUUID();
        this.patientID = patientID;
        this.weight = weight;
        this.regDate = new Date();
    }

    public Weight(){ }

}
