package com.measurelet.Model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Patient.class, parentColumns = "id", childColumns = "patientID"))
public class Bed {

    @PrimaryKey(autoGenerate = true)
    public UUID id;

    @ColumnInfo(name = "patientID")
    public UUID patientID;

    @ColumnInfo(name = "BedNum")
    public int bedNum;

    @ColumnInfo(name = "regDatetime")
    public Date regDatetime;


}
