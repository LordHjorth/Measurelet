package com.measurelet.Database.Database_Online;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildDatabase {

    private static DatabaseReference dbRef = OnlineDatabase.getAppDatabase();
    public static DatabaseReference dbPatientRef;


    public static void InsertPatient(Patient patient){
        HashMap<String, Object> map = new HashMap<>();
        map.put(Patient.patientID.toString(), patient);
        OnlineDatabase.getAppDatabase().updateChildren(map);
    }

    public static void UpdatePatient(){

    }

    public static void InsertNewIntake(Intake intake){
        List<String> childRefs = new ArrayList<>();
        childRefs.add(Patient.patientID.toString());
        childRefs.add("registrations");

        HashMap<String, Object> map = new HashMap<>();
        map.put(intake.intakeID.toString(), intake);

        OnlineDatabase.getChildDBReference(childRefs).updateChildren(map);

    }

    public static void UpdateNewIntake(Patient patient, Intake intake){

    }







}
