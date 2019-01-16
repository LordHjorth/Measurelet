package com.measurelet.Model;

import java.util.Date;
import java.util.UUID;

public class Intake {

    //TODO: Handle IV

    public String uuid;
    private String type;
    private int size;
    private Date timestamp;

    public Intake() {

    }

    public Intake(String type, int size) {
        this.uuid = UUID.randomUUID().toString();
        this.type = type;
        this.size = size;
        this.timestamp = new Date();
    }

    public Intake(String type, int size, String uuid, Date timestamp) {
        this.uuid = uuid;
        this.type = type;
        this.size = size;
        this.timestamp = timestamp;
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

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
