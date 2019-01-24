package com.measurelet.factories;

import com.example.hjorth.measurelet.R;
import com.measurelet.App;
import com.measurelet.model.Intake;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class IntakeFactory {

    public static void InsertNewIntake(List<Intake> allIntakes, Intake intake) {
        App.intakeRef.child(allIntakes.size()+ "").setValue(intake);
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

    public static ArrayList<Intake> getIntakesListWithDefaults(List<Intake> in){

        HashMap<String,Intake> d = new HashMap<>();
        d.put("Juice:175",new Intake().setType("Juice").setSize(175).setThumbnail(R.drawable.ic_orange_juice));
        d.put("Vand:175",new Intake().setType("Vand").setSize(175).setThumbnail(R.drawable.ic_glass_of_water));
        d.put("Kaffe:125",new Intake().setType("Kaffe").setSize(125).setThumbnail(R.drawable.ic_coffee_cup));
        d.put("Sodavand:175",new Intake().setType("Sodavand").setSize(175).setThumbnail(R.drawable.ic_soda));
        d.put("Vand:500",new Intake().setType("Vand").setSize(500).setThumbnail(R.drawable.ic_glass_of_water));

        List<String> unique = new ArrayList<>();

        ArrayList<Intake> result = new ArrayList<>();

        List<Intake> intakes = new ArrayList<Intake>(in);
        
         Collections.reverse(intakes);
        
        for( Intake i : intakes){
            String key = i.getType()+":"+i.getSize();
           if(d.containsKey(key)){
               i.setThumbnail(d.get(key).getThumbnail());
               d.remove(key);
           }

           if(!unique.contains(key)){
               result.add(i);
               unique.add(key);
           }

        }

        for(Intake i : d.values()){
            result.add(i);
        }

        return result;
    }

    
    

    //TODO: delete intakes
}

