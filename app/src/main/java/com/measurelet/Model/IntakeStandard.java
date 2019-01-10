package com.measurelet.Model;


import com.measurelet.Enums;

import java.util.Date;
import java.util.UUID;

public class IntakeStandard {


    public UUID id = UUID.randomUUID();

    public UUID patientID = UUID.randomUUID();

    public long amount;

    public Enums.IntakeType type;

    public Date regDate;

    public IntakeStandard(UUID patientID, Enums.IntakeType type, int amount){
        this.id = UUID.randomUUID();
        this.patientID = patientID;
        this.type = type;
        this.regDate = new Date();

        if(type != Enums.IntakeType.Custom){
            this.amount = type.amount;
        }
        else {
            this.amount = amount;
        }
    }

    public IntakeStandard(){ }

}
