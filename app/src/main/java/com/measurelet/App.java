package com.measurelet;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.measurelet.Model.Patient;

import java.io.IOException;

public class App extends Application {

    public static SharedPreferences preferenceManager;

    //Database instance
    private static FirebaseDatabase DB_INSTANCE;

    //Root reference
    public static DatabaseReference DB_REFERENCE;

    //Child references.
    public static DatabaseReference patientRef, intakeRef, weightRef;

    public static Boolean loggedIn = false;
    public static Patient currentUser;
    private static String key;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        // Required initialization logic here!
        FirebaseApp.initializeApp(this);
        AndroidThreeTen.init(this);

        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);

        // Verify if a user is logged in
        key = preferenceManager.getString("KEY", "");

        // We have a key in storage. Lets try to fetch the current user
        if (key.length() > 0) {
            setupRef(getAppDatabase(), key);
            loggedIn = true;
        }

        System.out.println("App is running");
        super.onCreate();
    }

    public static void setupRef(DatabaseReference rootRef, String key_string) {
        key = key_string;
        patientRef = rootRef.child("patients").child(key);
        patientRef.keepSynced(true);
        intakeRef = patientRef.child("registrations");
        weightRef = patientRef.child("weights");
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }


    public static DatabaseReference getAppDatabase() {


        if (DB_INSTANCE == null) {
            DB_INSTANCE = FirebaseDatabase.getInstance();
            DB_INSTANCE.setPersistenceEnabled(true);
            Log.d("Instance", "Instance created!");
        }
        if (DB_REFERENCE == null) {
            DB_REFERENCE = DB_INSTANCE.getReference();
            //DB_REFERENCE.addChildEventListener(updateChild);
            Log.d("Reference", "Reference created!");
        }

        return DB_REFERENCE;
    }

    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}

