package com.example.androidmusicbrainz.model;

public class Release {
    String mbid;
    String name;
    String date;

    public Release(String mbid, String name, String date) {
        this.mbid = mbid;
        this.name = name;
        this.date = date;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
