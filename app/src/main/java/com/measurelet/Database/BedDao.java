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
public interface BedDao {
    @Insert
    void insert(Bed bed);

    @Update
    void Update(Bed bed);

    @Delete
    void Delete(Bed bed);

    @Query("SELECT *  FROM Bed WHERE patientID = :patientID")
    Bed getPatientBed(UUID patientID);

    @Query("SELECT *  FROM Bed WHERE patientID = :patientID AND regDate = :regDate")
    Bed getPatientBedByTime(UUID patientID, Date regDate);

    @Query("SELECT * FROM Bed")
    List<Bed> getAllBeds();
}
