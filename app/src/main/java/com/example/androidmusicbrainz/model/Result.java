package com.example.androidmusicbrainz.model;

public class Result {
    String mbid;
    String text;
    String subtext;

    public Result(String mbid, String text, String subtext) {
        this.mbid = mbid;
        this.text = text;
        this.subtext = subtext;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }
}
