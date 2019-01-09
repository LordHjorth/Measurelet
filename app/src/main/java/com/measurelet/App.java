package com.measurelet;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.measurelet.Model.Patient;

public class App extends Application {

    public static Boolean HasloggedIn = false;


    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        FirebaseApp.initializeApp(this);
    }
}

