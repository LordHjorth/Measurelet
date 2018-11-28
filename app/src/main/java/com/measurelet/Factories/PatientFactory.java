package com.measurelet.Factories;

import com.measurelet.Database.LocalDatabase;
import com.measurelet.Model.Bed;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PatientFactory {

    private LocalDatabase db;

    public PatientFactory(LocalDatabase db){
        this.db = db;
    }

    public List<Patient> getAllPatients(){
        return db.patientDao().getAllPatients();
    }

    public Patient getPatient(UUID Id){
        return db.patientDao().getPatient(Id);
    }

    public Weight getPatientLatestWeight(UUID Id){
        return db.weightDao().getPatientLatestWeight(Id);
    }

    public void InsertPatientDetails(Patient patient, Weight weight, Bed bed){
        db.patientDao().insert(patient);
        db.weightDao().insert(weight);
        db.bedDao().insert(bed);
    }

    public Patient getCurrentPatient(){
        return db.patientDao().getFirstPatient();
    }

}
