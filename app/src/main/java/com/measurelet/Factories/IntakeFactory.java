package com.measurelet.Factories;

import com.measurelet.Database.IntakeDao;
import com.measurelet.Database.LocalDatabase;
import com.measurelet.Model.Intake;
import com.measurelet.Model.IntakeFav;
import com.measurelet.Model.IntakeStandard;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class IntakeFactory {

    private LocalDatabase db;

    public IntakeFactory(LocalDatabase db){
        this.db = db;
    }

    public void insertIntake(Intake intake){
        db.intakeDao().insert(intake);
    }

    public void updateIntake(Intake intake){
        db.intakeDao().Update(intake);
    }

    public void deleteIntake(Intake intake){
        db.intakeDao().Delete(intake);
    }

    public List<Intake> getIntakes(){
        return db.intakeDao().getAllIntakes();
    }

    public List<Intake> getPatientIntakes(UUID patientID){
        return db.intakeDao().getPatientIntake(patientID);
    }

    public Intake getSpecificIntake(UUID patientID, Date regDate){
        return db.intakeDao().getPatientIntakeByTime(patientID,regDate);
    }

    public int sumIntake(){
        List<Intake> intakes = db.intakeDao().getAllIntakes();
        int total = 0;

        for(Intake i : intakes){
            total += i.amount;
        }

        return total;
    }

    public void InsertIntakeFav(IntakeFav intakeFav){



    }

    public void DeleteIntakeFav(IntakeFav intakeFav){

    }

    public void InsertIntakeStandard(IntakeStandard intakeStandard){

    }

    public void DeleteIntakeStandard(IntakeStandard intakeStandard){

    }
}
