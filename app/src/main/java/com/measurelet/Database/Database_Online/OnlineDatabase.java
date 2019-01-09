package com.measurelet.Database.Database_Online;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.measurelet.Database.Database_Local.BedDao;
import com.measurelet.Database.Database_Local.IntakeDao;
import com.measurelet.Database.Database_Local.IntakeFavDao;
import com.measurelet.Database.Database_Local.IntakeStandardDao;
import com.measurelet.Database.Database_Local.PatientDao;
import com.measurelet.Database.Database_Local.WeightDao;

import java.util.List;


public abstract class OnlineDatabase {

    private static FirebaseDatabase DB_INSTANCE;
    private static DatabaseReference DB_REFERENCE;

    public abstract PatientDao patientDao();
    public abstract BedDao bedDao();
    public abstract IntakeDao intakeDao();
    public abstract IntakeFavDao intakeFavDao();
    public abstract IntakeStandardDao intakeStandardDao();
    public abstract WeightDao weightDao();

    public static DatabaseReference getAppDatabase() {


        if (DB_INSTANCE == null) {
            DB_INSTANCE = FirebaseDatabase.getInstance();
            DB_INSTANCE.setPersistenceEnabled(true);
            Log.d("Instance", "Instance created!");
        }
        if(DB_REFERENCE == null){
            DB_REFERENCE = DB_INSTANCE.getReference();
            Log.d("Reference", "Reference created!");
        }
        return DB_REFERENCE;
    }

    public static DatabaseReference getChildDBReference(List<String> childNames){
        DatabaseReference childRef = getAppDatabase();
        for(String name : childNames){
            childRef = childRef.child(name);
        }
        return childRef;
    }
}
