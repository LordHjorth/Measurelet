package com.measurelet.Database;

import com.measurelet.Model.Bed;
import com.measurelet.Model.Intake;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface IntakeDao {
    @Insert
    void insert(Intake intake);

    @Update
    void Update(Intake intake);

    @Delete
    void Delete(Intake intake);

    @Query("SELECT *  FROM Intake WHERE patientID = :patientID")
    Intake getPatientIntake(UUID patientID);

    @Query("SELECT *  FROM Intake WHERE patientID = :patientID AND regDate = :regDate")
    Intake getPatientIntakeByTime(UUID patientID, Date regDate);

    @Query("SELECT * FROM Intake")
    List<Intake> getAllIntakes();
}
