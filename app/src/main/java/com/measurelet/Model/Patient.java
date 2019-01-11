package com.measurelet.Model;

import java.util.ArrayList;
import java.util.UUID;


public class Patient {

    public String uuid;
    private String name;
    private int bedNum;

    private ArrayList<Weight> weights = new ArrayList<>();
    private ArrayList<Intake> registrations = new ArrayList<>();

    public Patient(String name, int bedNum) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.bedNum = bedNum;
    }

    public Patient(String name, int bedNum, String uuid, ArrayList<Intake> registrations, ArrayList<Weight> weights) {
        this.uuid = uuid;
        this.name = name;
        this.bedNum = bedNum;
        this.registrations = registrations;
        this.weights = weights;
    }

    public Patient() {

    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public ArrayList<Weight> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Weight> weights) {
        this.weights = weights;
    }

    public ArrayList<Intake> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(ArrayList<Intake> registrations) {
        this.registrations = registrations;
    }
}
