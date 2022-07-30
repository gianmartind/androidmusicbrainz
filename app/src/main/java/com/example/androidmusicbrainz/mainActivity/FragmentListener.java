package com.example.androidmusicbrainz.mainActivity;

public interface FragmentListener {
    void changePage(int page);
    void changeRelease(String mbid);
    void changeArtist(String mbid);
    void changeRecording(String mbid);
    void changeCountry(String mbid, String name);
    void changeResult(String type, String query);
    void loadSettings();
    void closeApplication();
    void restartApp();
}
