package com.example.androidmusicbrainz.model;

public class ItemGrid {
    protected String mbid;
    protected String image;
    protected String name;

    public ItemGrid(String mbid, String image, String name) {
        this.mbid = mbid;
        this.image = image;
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
