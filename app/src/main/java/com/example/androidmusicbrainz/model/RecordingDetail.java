package com.example.androidmusicbrainz.model;

public class RecordingDetail {
    String mbid;
    String name;
    String duration;
    String release;
    String artist;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public RecordingDetail(String mbid, String name, String duration, String release, String artist) {
        this.mbid = mbid;
        this.name = name;
        this.duration = duration;
        this.release = release;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        if(this.duration.equals(null) || this.duration.equals("null")){
            return "na";
        }
        int duration = Integer.parseInt(this.duration)/1000;
        int sec = (duration % 60);
        String seconds = sec < 10 ? "0" + sec : "" + sec;
        String inMinutes = ((duration - (duration % 60)) / 60) + ":" + seconds;
        return inMinutes;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
