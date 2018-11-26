package com.measurelet.Database;

import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeightDao {
    @Insert
    void insert(Weight weight);

    @Update
    void Update(Weight weight);

    @Delete
    void Delete(Weight weight);

    @Query("SELECT *  FROM Weight WHERE patientID = :patientID")
    Weight getPatientWeight(UUID patientID);

    @Query("SELECT *  FROM Weight WHERE patientID = :patientID AND regDate = :regDate")
    Weight getPatientWeightByTime(UUID patientID, Date regDate);

    @Query("SELECT * FROM Weight")
    List<Weight> getAllWeight();

}
