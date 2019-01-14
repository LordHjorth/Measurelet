package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Intake;

import java.util.ArrayList;
import java.util.List;

public class IntakeFactory {

    public static void InsertNewIntake(Intake intake) {

        List<Intake> intakes = App.currentUser == null ? new ArrayList<>() : App.currentUser.getRegistrations();
        intakes.add(intake);

        App.intakeRef.setValue(intakes);
    }

    public static void UpdateNewIntake(Intake intake) {
        App.intakeRef.child(intake.uuid).setValue(intake);
    }


    //TODO: delete intakes
}

