package com.measurelet;

public class DagVæske {
    String date;
    int mængde;

    public DagVæske(String date,int mængde){
        this.date=date;
        this.mængde=mængde;

    }

    public int getMængde() {
        return mængde;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMængde(int mængde) {
        this.mængde = mængde;
    }
}
