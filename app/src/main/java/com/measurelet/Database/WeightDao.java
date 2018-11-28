package com.measurelet.Database;

import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface WeightDao {
    @Insert
    void insert(Weight weight);

    @Update
    void Update(Weight weight);

    @Delete
    void Delete(Weight weight);

    @Query("SELECT *  FROM Weight WHERE patientID = :patientID ORDER BY regDate")
    List<Weight> getPatientWeights(UUID patientID);

    @Query("SELECT *  FROM Weight WHERE patientID = :patientID AND regDate = :regDate")
    Weight getPatientWeightByTime(UUID patientID, Date regDate);

    @Query("SELECT *  FROM Weight WHERE patientID = :patientID ORDER BY regDate DESC LIMIT 1")
    Weight getPatientLatestWeight(UUID patientID);

    @Query("SELECT * FROM Weight")
    List<Weight> getAllWeight();

}
