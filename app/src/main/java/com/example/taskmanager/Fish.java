package com.example.taskmanager;

import java.util.Date;

public class Fish {
    private String species, bait, misc;
    private double length, weight, temp;
    private Date date;
    private int id, taskID;

    public Fish(String species, double length, String bait, String misc, double weight, double temp, Date date, int id) {
        this.species = species;
        this.length = length;
        this.bait = bait;
        this.misc = misc;
        this.weight = weight;
        this.temp = temp;
        this.date = date;
        this.id = id;
    }
    public String getSpecies() {
        return species;
    }

    public String getBait() {
        return bait;
    }

    public String getMisc() {
        return misc;
    }

    public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }

    public double getTemp() {
        return temp;
    }

    public Date getDate() {
        return date;
    }

    public int getId(){
        return id;
    }

    public int getTaskID(){return taskID;}


    public void setSpecies(String species) {
        this.species = species;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTaskID(int taskID){
        this.taskID = taskID;
    }
}
