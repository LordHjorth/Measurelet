package com.measurelet.Database;

import android.content.Context;

import com.measurelet.Model.Bed;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Patient.class, Weight.class, Bed.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public abstract PatientDao scoreDao();

    public static LocalDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "db-galgeleg").allowMainThreadQueries().fallbackToDestructiveMigration().build();

            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
