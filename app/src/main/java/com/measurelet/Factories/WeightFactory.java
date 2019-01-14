package com.measurelet.Factories;

import com.measurelet.App;
import com.measurelet.Model.Weight;

import java.util.ArrayList;
import java.util.List;

public class WeightFactory {

    public static void InsertNewWeight(Weight weight) {
        List<Weight> weights = App.currentUser == null ? new ArrayList<>() : App.currentUser.getWeights();
        weights.add(weight);

        App.weightRef.setValue(weights);
    }

    public static void UpdateNewWeight(Weight weight) {
        App.weightRef.child(weight.uuid).setValue(weight);
    }


    //TODO: delete, update weight
}
