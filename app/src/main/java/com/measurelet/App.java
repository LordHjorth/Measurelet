package com.measurelet;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;
import com.measurelet.Database.Database_Local.LocalDatabase;
import com.measurelet.Database.Database_Online.OnlineDatabase;
import com.measurelet.Database.Database_Online.PatientFirebase;
import com.measurelet.Model.Patient;

public class App extends Application {

    public static SharedPreferences preferenceManager;

    public static Boolean loggedIn = false;


    public static PatientFirebase currentUser;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        FirebaseApp.initializeApp(this);
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);


        // Verify if a user is logged in

    }

    public static boolean isLoggedIn(){

        return false;
    }

    public static Patient getCurrentPatient(){
        return local_database.patientDao().getFirstPatient();
    }

}

