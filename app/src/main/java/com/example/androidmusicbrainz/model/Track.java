package com.example.androidmusicbrainz.model;

public class Track {
    String mbid;
    String trackName;
    String subtext;
    String trackDuration;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public Track(String mbid, String trackName, String subtext, String trackDuration) {
        this.mbid = mbid;
        this.trackName = trackName;
        this.subtext = subtext;
        this.trackDuration = trackDuration;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return subtext;
    }

    public void setArtistName(String subtext) {
        this.subtext = subtext;
    }

    public String getTrackDuration() {
        if(this.trackDuration.equals(null) || this.trackDuration.equals("null")){
            return "na";
        }
        int duration = Integer.parseInt(this.trackDuration)/1000;
        int sec = (duration % 60);
        String seconds = sec < 10 ? "0" + sec : "" + sec;
        String inMinutes = ((duration - (duration % 60)) / 60) + ":" + seconds;
        return inMinutes;
    }

    public void setTrackDuration(String trackDuration) {
        this.trackDuration = trackDuration;
    }
}
