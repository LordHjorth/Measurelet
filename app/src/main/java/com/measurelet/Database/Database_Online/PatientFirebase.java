package com.measurelet.Database.Database_Online;

import com.measurelet.Database.Database_Online.ChildDatabase;
import com.measurelet.Database.Database_Online.IntakeFirebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class PatientFirebase implements Observer {

    public static UUID patientID;
    String name;
    int bedNum;
    List<IntakeFirebase> intakes;

    public PatientFirebase(String name, int bedNum){
        this.name = name;
        this.bedNum = bedNum;
        this.patientID = UUID.randomUUID();
        this.intakes = new ArrayList<>();
    }

    public PatientFirebase(String name, int bedNum, UUID patientID){
        this.name = name;
        this.bedNum = bedNum;
        this.patientID = patientID;
    }

    @Override
    public void update(Observable o, Object arg) {
        //Når et patient objekt opdateres, så skal UI'en opdateres.
    }
}
