package com.measurelet.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.measurelet.Model.Intake;

@Dao
public interface IntakeStandardDao {

    @Insert
    void insert(Intake intake);

    @Update
    void Update(Intake intake);

    @Delete
    void Delete(Intake intake);

    //delete

    //update

    //getAllFavIntakes

}
