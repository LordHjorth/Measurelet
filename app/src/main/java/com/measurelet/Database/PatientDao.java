package com.measurelet.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.measurelet.Model.Bed;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Dao
public interface PatientDao {
    @Insert
    void insert(Patient patient);

    @Update
    void Update(Patient patient);

    @Delete
    void Delete(Patient patient);

    @Query("SELECT *  FROM Patient WHERE id = :ID")
    Patient getPatient(String ID);


    @Query("SELECT * FROM Patient")
    List<Patient> getAllPatients();


    @Query("SELECT * FROM Patient limit 1")
    Patient getFirstPatient();
}
