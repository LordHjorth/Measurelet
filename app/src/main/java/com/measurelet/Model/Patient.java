package com.measurelet.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Patient {

    public String uuid;
    private String name;
    private int bedNum;

    List<Weight> weights;
    List<Intake> intakes;

    public Patient(String name, int bedNum){
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.bedNum = bedNum;
        this.intakes = new ArrayList<>();
        this.weights = new ArrayList<>();
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

    public List<Intake> getIntakes() {
        return intakes;
    }

    public void setIntakes(List<Intake> intakes) {
        this.intakes = intakes;
    }


}
