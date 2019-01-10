package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Intake;

import java.util.HashMap;

public class IntakeFactory {

    public static void InsertNewIntake(Intake intake){
        HashMap<String, Object> map = new HashMap<>();
        map.put(intake.uuid, intake);

        App.intakeRef.updateChildren(map);
    }

    public static void UpdateNewIntake(Intake intake){
        App.intakeRef.child(intake.uuid).setValue(intake);
    }

}

