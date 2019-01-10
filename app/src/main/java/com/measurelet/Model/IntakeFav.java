package com.measurelet.Model;

import com.measurelet.Enums;
import com.measurelet.Factories.IntakeFactory;

import java.util.Date;
import java.util.UUID;



public class IntakeFav {


    private UUID id = UUID.randomUUID();
    private int amount;
    private Enums.IntakeType type;
    private Date regDate;

    public IntakeFav(Enums.IntakeType type, int amount){
        this.id = UUID.randomUUID();
        this.type = type;
        this.regDate = new Date();

        if(type != Enums.IntakeType.Custom){
            this.amount = type.amount;
        }
        else {
            this.amount = amount;
        }
    }

    public IntakeFav(){ }
}
