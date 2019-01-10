package com.measurelet.Model;

import java.util.Date;
import java.util.UUID;

public class Intake {


    public String uuid;
    private String type;
    private int size;
    private Date timestamp;

    public Intake(String type, int size){
        this.uuid = UUID.randomUUID().toString();
        this.type = type;
        this.size = size;
        this.timestamp = new Date();
    }

    public Intake(String uuid, String type, int size, Date date){
        this.uuid = uuid;
        this.type = type;
        this.size = size;
        this.timestamp = date;
    }

    public Intake(){

    }

}
