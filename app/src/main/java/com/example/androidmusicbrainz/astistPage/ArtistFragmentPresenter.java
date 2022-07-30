package com.example.androidmusicbrainz.astistPage;

import android.content.Context;

import com.example.androidmusicbrainz.model.Artist;
import com.example.androidmusicbrainz.model.Release;
import com.example.androidmusicbrainz.requests.LookupRequest;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragmentPresenter implements LookupRequest.ILookupRequest {
    String mbid;
    String areaMbid, areaName;
    List<Release> releaseList;
    IArtistFragment ui;

    public ArtistFragmentPresenter(String mbid, IArtistFragment ui){
        this.mbid = mbid;
        this.releaseList = new ArrayList<>();
        this.ui = ui;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public void loadData(Context context){
        LookupRequest lookupRequest = new LookupRequest(context, "artist", "releases", this);
        lookupRequest.makeRequest(this.mbid);
    }

    public void openArtistCountry(){
        this.ui.openArtistCountry(this.areaMbid, this.areaName);
    }

    @Override
    public void processResult(JSONObject result) throws JSONException {
        Logger.d(result);
        JSONArray releases = result.getJSONArray("releases");
        for(int i = 0; i < releases.length(); i++){
            String mbid = releases.getJSONObject(i).get("id").toString();
            String name = releases.getJSONObject(i).get("title").toString();
            String date = "";
            if(releases.getJSONObject(i).has("date")){
                date = releases.getJSONObject(i).get("date").toString();
            }
            Release release = new Release(mbid, name, date);
            this.releaseList.add(release);
        }
        this.ui.loadReleases(this.releaseList);
        String mbid = result.get("id").toString();
        String name = result.get("name").toString();
        String type = "";
        if(result.has("type")){
            type = result.get("type").toString();
        }
        String date = "";
        if(result.getJSONObject("life-span").has("begin")){
            date = result.getJSONObject("life-span").get("begin").toString();
        }
        String area = "";
        /*if(result.has("begin-area") && !result.get("begin-area").toString().equals("null")){
            if(result.getJSONObject("begin-area").has("name")){
                area = result.getJSONObject("begin-area").get("name").toString();
            }
        }*/
        if(result.has("area") &&  !result.isNull("area")){
            area = result.getJSONObject("area").get("name").toString();
            this.areaName = area;
            this.areaMbid = result.getJSONObject("area").get("id").toString();
        }
        Artist artist = new Artist(mbid, name, area, type, date);
        this.ui.loadData(artist);
    }

    @Override
    public void showError(String message) {

    }

    public interface IArtistFragment{
        void loadData(Artist artist);
        void loadReleases(List<Release> releaseList);
        void openArtistCountry(String mbid, String name);
    }
}
