package com.example.taskmanager;

import java.util.Date;

public class Fish {
    private String species, bait, weather;
    private double length, weight, temp;
    private Date date;
    private int id;

    public Fish(String species, double length, String bait) {
        this(species, length, bait, null, 0.0, 0.0);
        this.date = new Date();
    }

    public Fish(String species, double length, String bait, String weather) {
        this(species, length, bait, weather, 0.0, 0.0);
        this.date = new Date();
    }

    public Fish(double length, String species, String bait, double weight) {
        this(species, length, bait, null, weight, 0.0);
        this.date = new Date();
    }

    public Fish(String species, double length, String bait, double weight, double temp) {
        this(species, length, bait, null, weight, temp);
        this.date = new Date();
    }

    public Fish(double length,String species, String bait, String weather, double weight) {
        this(species, length, bait, weather, weight, 0.0);
        this.date = new Date();
    }


    public Fish(String species, double length, String bait, double temp) {
        this(species, length, bait, null, 0.0, temp);
        this.date = new Date();
    }

    public Fish(String species, double length, String bait, String weather, double temp) {
        this(species, length, bait, weather, 0.0, temp);
        this.date = new Date();
    }
    public Fish(String species, double length, String bait, String weather, double weight, double temp) {
        this.species = species;
        this.length = length;
        this.bait = bait;
        this.weather = weather;
        this.weight = weight;
        this.temp = temp;
        this.date = new Date();
        this.id = 0;
    }
    public String getSpecies() {
        return species;
    }

    public String getBait() {
        return bait;
    }

    public String getWeather() {
        return weather;
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


    public void setSpecies(String species) {
        this.species = species;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public void setWeather(String weather) {
        this.weather = weather;
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
}
