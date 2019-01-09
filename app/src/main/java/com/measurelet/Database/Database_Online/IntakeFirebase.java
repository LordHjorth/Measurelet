package com.measurelet.Database.Database_Online;

import java.util.Date;
import java.util.UUID;

public class IntakeFirebase {

    UUID intakeID;
    String type;
    int size;
    Date timestamp;

    public IntakeFirebase(String type, int size){
        this.intakeID = UUID.randomUUID();
        this.type = type;
        this.size = size;
        this.timestamp = new Date();
    }

    public IntakeFirebase(){

    }

}
