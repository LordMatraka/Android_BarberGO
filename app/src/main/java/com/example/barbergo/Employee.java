package com.example.barbergo;

public class Employee {

    private String name;
    private String description;
    private long imageResId;

    private String userId;
    public Employee(String name, String description, long imageResId, String userId){
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.userId = userId;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public int getImageResId(){return (int)this.imageResId; }

    public String getUserId() { return this.userId; }
}
