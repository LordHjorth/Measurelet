package com.measurelet.Database;

import com.measurelet.Model.Bed;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface BedDao {
    @Insert
    void insert(Bed bed);

    @Update
    void Update(Bed bed);

    @Delete
    void Delete(Bed bed);

    @Query("SELECT *  FROM Bed WHERE patientID = :patientID ORDER BY regDate DESC LIMIT 1")
    Bed getPatientBed(UUID patientID);

    @Query("SELECT *  FROM Bed WHERE patientID = :patientID AND regDate = :regDate")
    Bed getPatientBedByTime(UUID patientID, Date regDate);

    @Query("SELECT * FROM Bed")
    List<Bed> getAllBeds();
}
