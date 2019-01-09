package com.measurelet.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;


public class Patient implements Observer {

    public static UUID patientID;
    private String name;
    private int bedNum;
    private double weight;


    List<Intake> intakes;

    public Patient(String name, int bedNum){
        this.name = name;
        this.bedNum = bedNum;
        this.intakes = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        //Når et patient objekt opdateres, så skal UI'en opdateres.
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBedNum() {
        return bedNum;
    }

    public void setBedNum(int bedNum) {
        this.bedNum = bedNum;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Intake> getIntakes() {
        return intakes;
    }

    public void setIntakes(List<Intake> intakes) {
        this.intakes = intakes;
    }


}
