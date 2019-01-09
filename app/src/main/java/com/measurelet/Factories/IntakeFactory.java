package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Patient;

import java.util.HashMap;

public class IntakeFactory {

    public static void InsertNewIntake(Intake intake){

        HashMap<String, Object> map = new HashMap<>();
        map.put(intake.intakeID.toString(), intake);

        App.intakeRef.updateChildren(map);

    }

    public static void UpdateNewIntake(Patient patient, Intake intake){

    }

}

