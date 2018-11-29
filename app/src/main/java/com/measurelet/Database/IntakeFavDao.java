package com.measurelet.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.measurelet.Model.Intake;
import com.measurelet.Model.IntakeFav;

import java.util.List;
import java.util.UUID;


@Dao
public interface IntakeFavDao {

    @Insert
    void Insert(IntakeFav intake);

    @Update
    void Update(IntakeFav intake);

    @Delete
    void Delete(IntakeFav intake);

    @Query("SELECT *  FROM IntakeFav WHERE patientID = :patientID")
    List<IntakeFav> getPatientIntake(UUID patientID);

    //insert if not exist

    //delete

    //update

    //getAllFavIntakes
}
