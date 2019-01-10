package com.measurelet.Model;

import java.util.Date;
import java.util.UUID;

public class Weight {

    private double weightKG;
    private Date timestamp;
    public String uuid;

    public Weight(double weightKG){
        this.weightKG = weightKG;
        this.timestamp = new Date();
        this.uuid = UUID.randomUUID().toString();
    }

    public Weight(){

    }

}
