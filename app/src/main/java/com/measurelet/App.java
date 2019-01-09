package com.measurelet;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;
import com.measurelet.Model.Patient;

public class App extends Application {

    public static SharedPreferences preferenceManager;

    private static Boolean loggedIn = false;


    public static Patient currentUser;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        FirebaseApp.initializeApp(this);
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);


        // Verify if a user is logged in
        String key = preferenceManager.getString("KEY", null);

        // We have a key in storage. Lets try to fetch the current user
        if(key.length() > 0){


        }

    }

    public static boolean isLoggedIn(){

        return loggedIn;
    }


}

