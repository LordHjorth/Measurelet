package com.measurelet.Factories;

import com.measurelet.Database.LocalDatabase;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Weight;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GraphFactory {

    private LocalDatabase db;

    public GraphFactory(LocalDatabase db){
        this.db = db;
    }

    public HashMap<Date, Double> getPatientWeightGraph(UUID patientID){
        List<Weight> weights = db.weightDao().getPatientWeights(patientID);
        HashMap<Date, Double> weightMap = new HashMap<>();

        for(Weight w : weights){
            weightMap.put(w.regDate, w.weight);
        }
        return weightMap;
    }

    public HashMap<Date, Integer> getPatientIntakeGraph(UUID patientID){
        List<Intake> intakes = db.intakeDao().getPatientIntake(patientID);
        HashMap<Date, Integer> intakeMap = new HashMap<>();

        for(Intake i : intakes){
            intakeMap.put(i.regDate, i.amount);
        }

        return intakeMap;
    }


}
