package com.example.androidmusicbrainz.recordingPage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.androidmusicbrainz.database.Playlist;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.example.androidmusicbrainz.mainActivity.SettingsPrefSaver;
import com.example.androidmusicbrainz.model.RecordingDetail;
import com.example.androidmusicbrainz.requests.LookupRequest;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class RecordingFragmentPresenter implements LookupRequest.ILookupRequest {
    SettingsPrefSaver settingsPrefSaver;
    String mbid;
    String title;
    String artistMbid;
    String releaseMbid;
    String artistName;
    String releaseName;
    String searchLink;
    IRecordingFragment ui;

    public RecordingFragmentPresenter(String mbid, IRecordingFragment ui, Context context) {
        this.settingsPrefSaver = new SettingsPrefSaver(context);
        this.mbid = mbid;
        this.ui = ui;
        this.title = "";
        this.artistMbid = "";
        this.releaseMbid = "";
        this.searchLink = "";
        this.artistName = "";
        this.releaseName = "";
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public void loadData(Context context){
        LookupRequest lookupRequest = new LookupRequest(context, "recording", "artists+releases", this);
        lookupRequest.makeRequest(this.mbid);
    }

    public void openArtist(){
        this.ui.changePage(this.artistMbid, MainActivity.ARTIST);
    }

    public void openRelease(){
        this.ui.changePage(this.releaseMbid, MainActivity.RELEASE);
    }

    public void openYoutube(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + this.searchLink));
        context.startActivity(intent);
    }

    public void addToPlaylist(){
        if(this.settingsPrefSaver.getLogin().equals("")){
            this.ui.showToast("You need to login first!");
        } else {
            Playlist playlist = new Playlist();
            playlist.setUsername(this.settingsPrefSaver.getLogin());
            playlist.setMbid(this.mbid);
            playlist.setJudulLagu(this.title);
            playlist.setArtist(this.artistName);
            playlist.save();
            this.ui.showToast("Saved to playlist!");
        }
    }

    @Override
    public void processResult(JSONObject result) throws JSONException {
        Logger.d(result);
        this.artistMbid = result.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("id").toString();
        this.releaseMbid = result.getJSONArray("releases").getJSONObject(0).get("id").toString();
        this.artistName = result.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("name").toString();
        this.releaseName = result.getJSONArray("releases").getJSONObject(0).get("title").toString();
        this.title = result.get("title").toString();
        String length = result.get("length").toString();
        this.searchLink = title + " " + artistName;
        this.searchLink.replace(" ", "+");
        RecordingDetail recordingDetail = new RecordingDetail(this.releaseMbid, this.title, length, this.releaseName, this.artistName);
        this.ui.loadData(recordingDetail);
    }

    @Override
    public void showError(String message) {

    }

    public interface IRecordingFragment{
        void loadData(RecordingDetail recordingDetail);
        void changePage(String mbid, int page);
        void showToast(String message);
    }
}
