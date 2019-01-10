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

    public double getWeightKG() {
        return weightKG;
    }

    public void setWeightKG(double weightKG) {
        this.weightKG = weightKG;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
