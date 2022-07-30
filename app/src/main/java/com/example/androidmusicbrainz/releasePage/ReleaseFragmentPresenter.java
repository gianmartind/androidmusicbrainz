package com.example.androidmusicbrainz.releasePage;

import android.content.Context;
import android.util.Log;

import com.example.androidmusicbrainz.model.ReleaseDetails;
import com.example.androidmusicbrainz.model.Track;
import com.example.androidmusicbrainz.requests.LookupRequest;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ReleaseFragmentPresenter implements ReleaseDetailRequest.IReleaseRequest, LookupRequest.ILookupRequest {
    String mbid;
    String artistMbid;
    List<Track> trackList;
    IReleaseFragment ui;

    public ReleaseFragmentPresenter(String mbid, IReleaseFragment ui){
        this.mbid = mbid;
        this.trackList = new ArrayList<>();
        this.ui = ui;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public void loadData(Context context){
//        this.getDetails(context);
//        this.getTracks(context);
        LookupRequest lookupRequest = new LookupRequest(context, "release", "artists+recordings", this);
        lookupRequest.makeRequest(this.mbid);
    }

    public void getDetails(Context context){
        ReleaseDetailRequest releaseDetailRequest = new ReleaseDetailRequest("details", context, this);
        releaseDetailRequest.makeRequest(this.mbid);
    }

    public void getTracks(Context context){
        ReleaseDetailRequest releaseDetailRequest = new ReleaseDetailRequest("tracks", context, this);
        releaseDetailRequest.makeRequest(this.mbid);
    }

    public void openArtist(){
        this.ui.openArtist(this.artistMbid);
    }

    @Override
    public void processDetails(JSONObject json) throws JSONException {
        String year = "";
        if(json.has("date")){
            year = json.get("date").toString();
        }
        String title = json.get("title").toString();
        String artist = json.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("name").toString();
        this.artistMbid = json.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("id").toString();
        String mbid = json.get("id").toString();
        ReleaseDetails releaseDetails = new ReleaseDetails(mbid, title, artist, year);
        this.ui.loadDetails(releaseDetails);
    }

    @Override
    public void processTracks(JSONObject json) throws JSONException {
        JSONArray jsonArray = json.getJSONArray("recordings");
        for(int i = 0; i < jsonArray.length(); i++){
            String mbid = jsonArray.getJSONObject(i).get("id").toString();
            String title = jsonArray.getJSONObject(i).get("title").toString();
            String disambiguation = jsonArray.getJSONObject(i).get("disambiguation").toString();
            String duration = jsonArray.getJSONObject(i).get("length").toString();
            this.trackList.add(new Track(mbid, title, disambiguation, duration));
        }
        this.ui.loadTracks(this.trackList);
    }

    @Override
    public void processResult(JSONObject result) throws JSONException {
        Logger.d(result);
        String year = "";
        if(result.has("date")){
            year = result.get("date").toString();
        }
        String title = result.get("title").toString();
        String artist = result.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("name").toString();
        this.artistMbid = result.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("id").toString();
        String mbid = result.get("id").toString();
        ReleaseDetails releaseDetails = new ReleaseDetails(mbid, title, artist, year);
        this.ui.loadDetails(releaseDetails);

        JSONArray jsonArray = result.getJSONArray("media").getJSONObject(0).getJSONArray("tracks");
        for(int i = 0; i < jsonArray.length(); i++){
            String trackMbid = jsonArray.getJSONObject(i).getJSONObject("recording").get("id").toString();
            String trackTitle = jsonArray.getJSONObject(i).getJSONObject("recording").get("title").toString();
            String disambiguation = "";
            if(jsonArray.getJSONObject(i).getJSONObject("recording").has("disambiguation")){
                disambiguation = jsonArray.getJSONObject(i).getJSONObject("recording").get("disambiguation").toString();
            }
            String duration = jsonArray.getJSONObject(i).getJSONObject("recording").get("length").toString();
            this.trackList.add(new Track(trackMbid, trackTitle, disambiguation, duration));
        }
        this.ui.loadTracks(this.trackList);
    }

    @Override
    public void showError(String message) {

    }

    public interface IReleaseFragment{
        void loadDetails(ReleaseDetails releaseDetails);
        void loadTracks(List<Track> trackList);
        void openArtist(String mbid);
    }
}
