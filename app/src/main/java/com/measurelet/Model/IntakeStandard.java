package com.measurelet.Model;


import java.util.Date;
import java.util.UUID;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"))
public class IntakeStandard {

    @PrimaryKey(autoGenerate = true)
    public UUID id;

    @ColumnInfo(name = "patientID")
    public UUID patientID;

    @ColumnInfo(name = "Amount")
    public long amount;

    @ColumnInfo(name = "Type")
    public String type;

    @ColumnInfo(name = "regDatetime")
    public Date regDatetime;

}
