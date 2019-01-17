package com.measurelet.Model;

import com.google.firebase.database.Exclude;

import java.time.LocalDateTime;
import java.util.UUID;

public class Intake {

    //TODO: Handle IV

    public String uuid;
    private String type;
    private int size;
    private String timestamp;

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
