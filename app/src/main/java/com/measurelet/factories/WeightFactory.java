package com.measurelet.factories;

import com.google.firebase.database.Exclude;
import com.measurelet.App;
import com.measurelet.model.Intake;
import com.measurelet.model.Weight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WeightFactory {


    public static void InsertNewWeight(List<Weight> weights, Weight weight) {
        App.weightRef.child(weights.size()+ "").setValue(weight);
    }

    public static ArrayList<Weight> getSortedWeights(ArrayList<Weight> weights) {

        weights.removeIf(Objects::isNull);

        Collections.sort(weights, (o2, o1) -> {
            if (o1.getDatetime().isEqual(o2.getDatetime())) {
                return 0;
            }
            return o1.getDatetime().isAfter(o2.getDatetime()) ? -1 : 1;
        });

        return weights;
    }

}
