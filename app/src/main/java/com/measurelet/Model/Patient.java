package com.measurelet.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Patient {

    public String uuid;
    private String name;
    private int bedNum;

    private ArrayList<Weight> weights;
    private ArrayList<Intake> intakes;

    public Patient(String name, int bedNum){
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.bedNum = bedNum;
        this.intakes = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    public Patient(){

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
}
