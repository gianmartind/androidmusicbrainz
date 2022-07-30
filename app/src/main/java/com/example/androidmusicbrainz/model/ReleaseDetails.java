package com.example.androidmusicbrainz.model;

public class ReleaseDetails {
    String mbid;
    String title;
    String artist;
    String year;

    public ReleaseDetails(String mbid, String title, String artist, String year) {
        this.mbid = mbid;
        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
