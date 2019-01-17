package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Intake;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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


    public static HashMap<String, Integer> getIntakePrHour(ArrayList<Intake> dailyIntake){

        DateTimeFormatter formatHour = DateTimeFormatter.ofPattern("HH");
        HashMap<String, Integer> hourMap = new HashMap<>();

        //samler mængder efter time
        int mængde = 0;
        for (Intake intake : dailyIntake) {
            String hour = intake.getDateTime().format(formatHour);
            if (hourMap.containsKey(hour)) {
                mængde = mængde + intake.getSize();
            } else {
                mængde = intake.getSize();
            }

            hourMap.put(hour, mængde);
        }


        return hourMap;
    }


    //TODO: delete intakes
}

