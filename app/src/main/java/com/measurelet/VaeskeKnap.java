package com.measurelet;

public class VaeskeKnap {
    private String type;
    private int mængde;
    private int thumbnail;

    public VaeskeKnap(){

    }

    public VaeskeKnap(String type, int mængde, int thumbnail){
        this.mængde=mængde;
        this.type=type;
        this.thumbnail=thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
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
}
