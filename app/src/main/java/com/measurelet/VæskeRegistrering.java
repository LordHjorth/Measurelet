package com.measurelet;

import java.util.Date;

public class VæskeRegistrering {


    Date date ;
    int mængde;
    String type;
    String navn;
    public VæskeRegistrering(){

    }

    public VæskeRegistrering(Date date,int mængde){
    this.date=date;
    this.mængde=mængde;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMængde() {
        return mængde;
    }

    public void setMængde(int mængde) {
        this.mængde = mængde;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }



}
