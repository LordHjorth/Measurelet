package com.measurelet;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import java.util.concurrent.Semaphore;

public class App extends Application {

    public static SharedPreferences preferenceManager;

    //Database instance
    private static FirebaseDatabase DB_INSTANCE;

    //Root reference
    public static DatabaseReference DB_REFERENCE;

    //Child references.
    public static DatabaseReference patientRef, intakeRef, weightRef;

    private static Boolean loggedIn = false;
    public static Patient currentUser;
    private static String key;
    private static Semaphore sem = new Semaphore(0, true);


    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        FirebaseApp.initializeApp(this);
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);

        // Verify if a user is logged in
        key = preferenceManager.getString("KEY", "");

        // We have a key in storage. Lets try to fetch the current user
        if (key.length() > 0) {
            setupRef(getAppDatabase(), key);
            //TODO: fix this. Add spinner while loading
            new AsyncTask() {
                @Override
                protected void onPreExecute() {

                }

                @Override
                protected void onPostExecute(Object o) {

                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    referenceStartUp();
                    return null;
                }
            }.execute();

            loggedIn = true;
        }
    }

    public static void setupRef(DatabaseReference rootRef, String key_string) {
        key = key_string;
        patientRef = rootRef.child("patients").child(key);
        intakeRef = patientRef.child("registrations");
        weightRef = patientRef.child("weights");
    }

    public static void referenceStartUp() {

        sem = new Semaphore(0);

        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());

                currentUser = dataSnapshot.getValue(Patient.class);

                System.out.println("Succeeded \n" + currentUser.getName());
                sem.release();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Cancelled");
                sem.release();
            }
        });

        try {
            sem.acquire();
        } catch (InterruptedException e) {
            Log.w("APP", "Interrupted");
        }

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
}

