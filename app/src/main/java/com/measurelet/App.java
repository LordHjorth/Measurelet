package com.measurelet;

import android.app.Application;

import com.measurelet.Database.LocalDatabase;
import com.measurelet.Model.Patient;

public class App extends Application {

    public static Boolean HasloggedIn = false;

    public static LocalDatabase database;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
            database = LocalDatabase.getAppDatabase(this);
    }


    public static Patient getCurrentPatient(){
        return database.patientDao().getFirstPatient();
    }

}


