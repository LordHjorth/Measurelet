package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Patient;

import java.util.HashMap;

public class PatientFactory {

    public static void InsertPatient(Patient patient){
        HashMap<String, Object> map = new HashMap<>();
        map.put(patient.uuid.toString(), patient);
        App.getAppDatabase().updateChildren(map);
    }

    public static void UpdatePatient(){

    }

}
