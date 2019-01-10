package com.measurelet;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.Model.Patient;

import java.util.List;

public class App extends Application {

    public static SharedPreferences preferenceManager;

    //Database instance
    private static FirebaseDatabase DB_INSTANCE;

    //Root reference
    public static DatabaseReference DB_REFERENCE;

    //Child references.
    public static DatabaseReference patientRef;
    public static DatabaseReference intakeRef;
    public static DatabaseReference weightRef;
    public static DatabaseReference uuidRef;
    public static DatabaseReference bedNumRef;
    public static DatabaseReference nameRef;

    private static Boolean loggedIn = false;

    public static Patient currentUser;
    public static List<Patient> activeUsers;


    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        FirebaseApp.initializeApp(this);
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);

        // Verify if a user is logged in
        String key = preferenceManager.getString("KEY", "");

        // We have a key in storage. Lets try to fetch the current user
        if (key.length() > 0) {
            referenceStartUp(getAppDatabase(), key);
            loggedIn = true;
        }

    }

    public static void referenceStartUp(DatabaseReference rootRef, String key) {
        patientRef = rootRef.child(key);
        intakeRef = patientRef.child("registrations");
        weightRef = patientRef.child("weights");
        uuidRef = patientRef.child("uuid");
        bedNumRef = patientRef.child("bedNum");
        nameRef = patientRef.child("name");

        patientRef.addListenerForSingleValueEvent(update);
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


    private static ValueEventListener update = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            System.out.println(dataSnapshot.getValue());

            currentUser = dataSnapshot.getValue(Patient.class);

            System.out.println("Succeeded \n" + currentUser.getName());


        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println("Cancelled");
        }
    };
}

