package com.example.androidmusicbrainz.artistCountry;

import android.content.Context;
import android.util.Log;

import com.example.androidmusicbrainz.model.Artist;
import com.example.androidmusicbrainz.requests.BrowseRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtistCountryFragmentPresenter implements BrowseRequest.IBrowseRequest {
    String mbid;
    List<Artist> list;
    IArtistCountryFragment ui;
    int offset;

    public ArtistCountryFragmentPresenter(String mbid, IArtistCountryFragment ui){
        this.mbid = mbid;
        this.ui = ui;
        this.list = new ArrayList<>();
        this.offset = 0;
    }

    public void loadData(Context context){
        BrowseRequest browseRequest = new BrowseRequest(context, "artist", "area", this);
        browseRequest.makeRequest(this.mbid + "&offset=" + this.offset);
        this.offset += 26;
    }

    @Override
    public void processResult(JSONObject result) throws JSONException {
        JSONArray jsonArray = result.getJSONArray("artists");
        if(jsonArray.length() > 0){
            for(int i = 0; i < jsonArray.length(); i++){
                String mbid = jsonArray.getJSONObject(i).get("id").toString();
                String name = jsonArray.getJSONObject(i).get("name").toString();
                String type = "";
                if(jsonArray.getJSONObject(i).has("type")){
                    type = jsonArray.getJSONObject(i).get("type").toString();
                }
                String date = "";
                if(jsonArray.getJSONObject(i).getJSONObject("life-span").has("begin")){
                    date = jsonArray.getJSONObject(i).getJSONObject("life-span").get("begin").toString();
                }
                String area = "";
                if(jsonArray.getJSONObject(i).has("country")){
                    area = jsonArray.getJSONObject(i).get("country").toString();
                }
                Artist artist = new Artist(mbid, name, area, type, date);
                this.list.add(artist);
            }
            this.ui.updateList(this.list);
        }
    }

    @Override
    public void showError(String message) {
        this.ui.showError(message);
    }



    public interface IArtistCountryFragment{
        void updateList(List<Artist> list);
        void showError(String message);
    }
}
