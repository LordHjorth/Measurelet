package com.measurelet.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.measurelet.Model.IntakeFav;


@Dao
public interface IntakeFavDao {

    @Insert
    void insert(IntakeFav intake);

    @Update
    void Update(IntakeFav intake);

    @Delete
    void Delete(IntakeFav intake);

    //insert if not exist

    //delete

    //update

    //getAllFavIntakes
}
