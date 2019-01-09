package com.measurelet.Factories;

import com.google.firebase.database.DatabaseReference;
import com.measurelet.Database.Database_Local.LocalDatabase;
import com.measurelet.Model.Bed;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import java.util.List;
import java.util.UUID;

public class PatientFactory {

    //region Room local_database
    private LocalDatabase local_db;
    private DatabaseReference patientRef;

    public PatientFactory(LocalDatabase ldb){

        this.local_db = ldb;
        //this.patientRef = ChildDatabase.getPatientRef()

    }

    public List<Patient> getAllPatients(){
        return local_db.patientDao().getAllPatients();
    }

    public Patient getPatient(UUID Id){
        return local_db.patientDao().getPatient(Id);
    }

    public Weight getPatientLatestWeight(UUID Id){
        return local_db.weightDao().getPatientLatestWeight(Id);
    }

    public void InsertPatientDetails(Patient patient, Weight weight, Bed bed){
        local_db.patientDao().insert(patient);
        local_db.weightDao().insert(weight);
        local_db.bedDao().insert(bed);
    }

    public Patient getCurrentPatient(){

        return local_db.patientDao().getFirstPatient();
    }

    //endregion



    //region Firebase local_database
/*
    public void update(){

        fireBase = FirebaseDatabase.getInstance().getReference();

        fireBase.child("Rasmus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Jobs done", "ML:");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

*/
    //endregion
}
