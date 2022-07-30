package com.example.androidmusicbrainz.model;

public class Artist {
    String mbid;
    String name;
    String area;
    String type;
    String date;

    public Artist(String mbid, String name, String area, String type, String date) {
        this.mbid = mbid;
        this.name = name;
        this.area = area;
        this.type = type;
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

    public String getArea() {
        if(this.area.equals("null")){
            return "";
        } else {
            return this.area;
        }
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        if(this.type.equals("null")){
            return "";
        } else {
            return this.type;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        if(this.date.equals("null")){
            return "";
        } else {
            return this.date;
        }
    }

    public void setDate(String date) {
        this.date = date;
    }
}
