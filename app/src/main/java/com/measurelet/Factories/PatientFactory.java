package com.measurelet.Factories;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.measurelet.App;
import com.measurelet.Model.Patient;
import com.measurelet.Model.Weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientFactory {

    public static void InsertPatient(Patient patient) {

        boolean success = false;
        Task task = App.patientRef.setValue(patient);

        try {
            Tasks.await(task);
            success = task.isSuccessful();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("MY_TAG", "successfull operation:" + success);


    }

    public static void UpdatePatient() {

    }

}
