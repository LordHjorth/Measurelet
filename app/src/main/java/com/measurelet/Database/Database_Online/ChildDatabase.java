package com.measurelet.Database.Database_Online;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.measurelet.App;
import com.measurelet.Model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildDatabase {

    private static DatabaseReference dbRef = OnlineDatabase.getAppDatabase();
    public static DatabaseReference dbPatientRef;
    public static int patientCount = 0;


    public void getPatientDatabase(int patientBedNum) {

        dbPatientRef = dbRef.child("bed_" + patientBedNum);

        dbPatientRef.addValueEventListener(updatePatient);

    }

    public static void InsertPatient(PatientFirebase patient){
        HashMap<String, Object> map = new HashMap<>();
        map.put(patient.patientID.toString(), patient);
        OnlineDatabase.getAppDatabase().updateChildren(map);
        OnlineDatabase.getAppDatabase().child("patient_count").setValue(patientCount + 1);
    }

    public static void UpdatePatient(){

    }

    public static void InsertNewIntake(IntakeFirebase intake){
        List<String> childRefs = new ArrayList<>();
        childRefs.add(PatientFirebase.patientID.toString());
        childRefs.add("registrations");

        HashMap<String, Object> map = new HashMap<>();
        map.put(intake.intakeID.toString(), intake);

        OnlineDatabase.getChildDBReference(childRefs).updateChildren(map);
    }

    public static void UpdateNewIntake(PatientFirebase patient, IntakeFirebase intake){

    }





    private static ValueEventListener updatePatient = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            patientCount = dataSnapshot.child("patient_count").getValue(Integer.class);
            System.out.println("Succeeded \nPatient count: " + patientCount);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            System.out.println("Cancelled");
        }
    };

}
