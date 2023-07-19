package com.example.taskmanager;

import static com.example.taskmanager.view_task.taskList;

import java.util.ArrayList;

public class Task
{
    private String name;
    private String description;
    private boolean isComplete;
    private int id;
    public ArrayList<Fish> fishList;

    public Task(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.isComplete = false;
        this.id = taskList.size();
        this.fishList = new ArrayList<Fish>();

    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setComplete(boolean complete)
    {
        isComplete = complete;
    }

    public String toString()
    {
        return String.format("Task name: %s\nDescription: %s", getName(), getDescription());
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public void setFishList(Fish fish){fishList.add(fish);}

    public ArrayList<Fish> getFish(){
        return fishList;
    }

}
