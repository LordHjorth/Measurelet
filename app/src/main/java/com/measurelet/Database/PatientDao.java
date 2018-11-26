package com.measurelet.Database;

import com.measurelet.Model.Bed;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PatientDao {
    @Insert
    void insert(Patient patient);

    @Update
    void Update(Patient patient);

    @Delete
    void Delete(Patient patient);

    @Query("SELECT *  FROM Patient WHERE id = :ID")
    Patient getPatient(UUID ID);


    @Query("SELECT * FROM Patient")
    List<Patient> getAllPatients();
}
