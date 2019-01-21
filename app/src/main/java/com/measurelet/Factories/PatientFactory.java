package com.measurelet.Factories;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.measurelet.App;
import com.measurelet.Model.Patient;

import java.util.HashMap;
import java.util.Map;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("MY_TAG", "successfull operation:" + success);


    }

    public static void UpdatePatient(String name, int bed) {

        DatabaseReference patientRef = App.patientRef;
        Map<String, Object> updates = new HashMap<>();

        updates.put("name", name);
        updates.put("bedNum", bed);

        patientRef.updateChildren(updates);
    }


    //TODO: update patient

}
