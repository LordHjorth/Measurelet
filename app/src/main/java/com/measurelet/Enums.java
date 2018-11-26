package com.measurelet;

public class Enums {



    public enum IntakeType{
        Cup(175),
        Glas(175),
        Mug(175),
        Bowl(350),
        Bottle(500),
        Pitcher(1000),
        Custom(0);

        public int amount;
        IntakeType(int amount){
            this.amount = amount;
        }
    }

}
