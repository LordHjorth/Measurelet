package com.measurelet;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.measurelet.Database.Database_Local.LocalDatabase;
import com.measurelet.Database.Database_Online.OnlineDatabase;
import com.measurelet.Model.Patient;

public class App extends Application {

    public static Boolean HasloggedIn = false;

    public static LocalDatabase local_database;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        local_database = LocalDatabase.getAppDatabase(this);
        FirebaseApp.initializeApp(this);
    }


    public static Patient getCurrentPatient(){
        return local_database.patientDao().getFirstPatient();
    }

}


