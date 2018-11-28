package com.measurelet.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.measurelet.Database.Converters.DateConverter;
import com.measurelet.Database.Converters.IDConverter;
import com.measurelet.Database.Converters.IntakeTypeConverter;
import com.measurelet.Model.Bed;
import com.measurelet.Model.Intake;
import com.measurelet.Model.IntakeFav;
import com.measurelet.Model.IntakeStandard;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

@Database(entities = {Patient.class, Weight.class, Bed.class, Intake.class, IntakeFav.class, IntakeStandard.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, IDConverter.class, IntakeTypeConverter.class})
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public abstract PatientDao patientDao();
    public abstract BedDao bedDao();
    public abstract IntakeDao intakeDao();
    public abstract IntakeFavDao intakeFavDao();
    public abstract IntakeStandardDao intakeStandardDao();
    public abstract WeightDao weightDao();


    public static LocalDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "db-galgeleg").allowMainThreadQueries().fallbackToDestructiveMigration().build();

            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
