package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientFactory {

    public static void InsertPatient(Patient patient) {
        App.patientRef.setValue(patient);
    }

    public static void UpdatePatient() {

    }

}
