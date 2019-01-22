package com.measurelet.Model;

import com.example.hjorth.measurelet.R;
import com.google.firebase.database.Exclude;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;
import java.util.UUID;

public class Intake {

    //TODO: Handle IV

    public String uuid;
    private String type;
    private int size;
    private String timestamp;


    private int thumbnail;

    public Intake() {

    }

    public Intake(String type, int size) {
        this.uuid = UUID.randomUUID().toString();
        this.type = type;
        this.size = size;
        this.timestamp = LocalDateTime.now().toString();
    }

    public Intake(String type, int size, String uuid, String timestamp) {
        this.uuid = uuid;
        this.type = type;
        this.size = size;
        this.timestamp = timestamp;
    }


    @Exclude
    public int getThumbnail(){
        if(thumbnail == 0){

            if(Arrays.asList("sodavand", "cola", "fanta", "danskvand").contains(getType().toLowerCase())){
                return R.drawable.ic_soda;
            }

            if(Arrays.asList("kaffe", "caffe latte", "latte").contains(getType().toLowerCase())){
                return R.drawable.ic_coffee_cup;
            }


            if(Arrays.asList("saftevand", "saft", "juice", "æblejuice", "appelsinjuice").contains(getType().toLowerCase())){
                return R.drawable.ic_orange_juice;
            }

            return  R.drawable.ic_glass_of_water;
        }

        return thumbnail;
    }

    public Intake setThumbnail(int tumb){
        this.thumbnail = tumb;
        return this;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public Intake setType(String type) {
        this.type = type;

        return this;
    }

    public int getSize() {
        return size;
    }

    public Intake setSize(int size) {
        this.size = size;

        return this;
    }

    @Exclude
    public LocalDateTime getDateTime() {
        return LocalDateTime.parse(timestamp);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
