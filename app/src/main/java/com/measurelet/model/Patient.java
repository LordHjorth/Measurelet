package com.measurelet.model;

import com.google.firebase.database.Exclude;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;


public class Patient {

    public String uuid;
    private String name;
    private int bedNum;

    private ArrayList<Weight> weights = new ArrayList<>();
    private ArrayList<Intake> registrations = new ArrayList<>();

    public Patient() {

    }

    public Patient(String name, int bedNum) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.bedNum = bedNum;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBedNum() {
        return bedNum;
    }

    public void setBedNum(int bedNum) {
        this.bedNum = bedNum;
    }

    public ArrayList<com.measurelet.model.Weight> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Weight> weights) {
        this.weights = weights;
    }

    public ArrayList<com.measurelet.model.Intake> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(ArrayList<Intake> registrations) {
        this.registrations = registrations;
    }


    @Exclude
    public ArrayList<com.measurelet.model.Intake> getIntakesForDate(LocalDate date) {
        registrations.removeIf(Objects::isNull);
        ArrayList<Intake> intakesCurrentDate = new ArrayList<>();
        for (Intake i : registrations) {
            if (i.getDateTime().getDayOfMonth() == date.getDayOfMonth() && i.getDateTime().getMonthValue() == date.getMonthValue()) {
                intakesCurrentDate.add(i);
            }
        }
        //SORT
        Collections.sort(intakesCurrentDate, (o1, o2) -> {
            if (o1.getDateTime().isEqual(o2.getDateTime())) {
                return 0;
            }
            return o1.getDateTime().isAfter(o2.getDateTime()) ? -1 : 1;
        });

        return intakesCurrentDate;
    }

    @Exclude
    public SortedMap<String, Integer> getIntakesForWeeks() {
        registrations.removeIf(Objects::isNull);
        ArrayList<Intake> intakesCurrentDate = registrations;
        SortedMap<String, Integer> intakesPerDay = new TreeMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");



        //SORT
        Collections.sort(intakesCurrentDate, (o1, o2) -> {
            if (o1.getDateTime().isEqual(o2.getDateTime())) {
                return 0;
            }
            return o1.getDateTime().isAfter(o2.getDateTime()) ? -1 : 1;
        });

        for (Intake i : intakesCurrentDate) {
            int amount = i.getSize();
            if (intakesPerDay.containsKey(i.getDateTime().format(dateTimeFormatter))) {
                amount += intakesPerDay.get(i.getDateTime().format(dateTimeFormatter));
            }

            intakesPerDay.put(i.getDateTime().format(dateTimeFormatter), amount);
        }

        return intakesPerDay;
    }
}
