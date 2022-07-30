package com.example.androidmusicbrainz.model;

public class ItemLeftDrawer {
    private int id;
    private String item;

    public ItemLeftDrawer(int id, String item){
        this.id = id;
        this.item = item;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setItem(String item){
        this.item = item;
    }

    public int getId(){
        return id;
    }

    public String getItem(){
        return item;
    }
}
