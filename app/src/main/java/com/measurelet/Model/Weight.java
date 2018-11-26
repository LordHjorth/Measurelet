package com.measurelet.Model;

import java.util.Date;
import java.util.UUID;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"))
public class Weight {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "patientID")
    public UUID patientID;

    @ColumnInfo(name = "regDate")
    public Date regDate;

    @ColumnInfo(name = "Weight")
    public double weight;

}
