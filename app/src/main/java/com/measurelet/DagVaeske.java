package com.measurelet;

public class DagVaeske {

    //TODO: Use intake instead -> then delete. Rename

    String date;
    int mængde;

    public DagVaeske(String date,int mængde){
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
