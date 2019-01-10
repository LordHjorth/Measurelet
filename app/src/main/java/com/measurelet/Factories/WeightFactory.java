package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Intake;
import com.measurelet.Model.Weight;

import java.util.HashMap;

public class WeightFactory {

    public static void InsertNewWeight(Weight weight){
        HashMap<String, Object> map = new HashMap<>();
        map.put(weight.uuid, weight);

        App.weightRef.updateChildren(map);
    }

    public static void UpdateNewWeight(Weight weight){
        App.weightRef.child(weight.uuid).setValue(weight);
    }
}
