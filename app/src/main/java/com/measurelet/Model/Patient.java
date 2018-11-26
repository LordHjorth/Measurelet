package com.measurelet.Model;

import java.util.List;
import java.util.UUID;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Patient {

    @PrimaryKey(autoGenerate = true)
    public UUID id;

    @ColumnInfo(name = "PatientName")
    public String patientName;

}
