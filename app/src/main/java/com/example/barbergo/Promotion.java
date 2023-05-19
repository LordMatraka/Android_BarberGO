package com.example.barbergo;

public class Promotion {
    private String title;
    private String content;
    private long imageResId;
    public Promotion(String title, String content, long imageResId){
        this.title = title;
        this.content = content;
        this.imageResId = imageResId;
    }
    public String getTitle(){

        return this.title;
    }
    public String getContent(){

        return this.content;
    }
    public int getImageResId(){
        return (int)this.imageResId;
    }
}
