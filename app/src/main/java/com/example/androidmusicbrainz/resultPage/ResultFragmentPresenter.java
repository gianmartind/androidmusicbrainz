package com.example.androidmusicbrainz.resultPage;

import android.content.Context;
import android.util.Log;

import com.example.androidmusicbrainz.model.Result;
import com.example.androidmusicbrainz.requests.SearchRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ResultFragmentPresenter implements SearchRequest.ISearchRequest {
    String type;
    String query;
    List<Result> resultList;
    IResultFragment ui;
    int offset;

    public ResultFragmentPresenter(String type, String query, IResultFragment ui){
        this.type = type;
        this.query = query;
        this.ui = ui;
        this.resultList = new ArrayList<>();
        this.offset = 0;
    }

    public void loadData(Context context){
        String type2;
        String query2;
        if(this.type.equals("Artist by type")){
            type2 = "artist";
            query2 = "type:" + this.query.toLowerCase() + "&offset=" + this.offset;
        } else if (this.type.equals("Artist by area")){
            type2 = "artist";
            query2 = "area:" + this.query.toLowerCase() + "&offset=" + this.offset;
        } else{
            type2 = this.type.toLowerCase();
            query2 = this.query.toLowerCase() + "&offset=" + this.offset;
        }

        SearchRequest searchRequest = new SearchRequest(context, type2, query2, this);
        searchRequest.makeRequest();
        this.offset += 26;
    }

    @Override
    public void processResult(JSONObject result) throws JSONException {
        if(this.type.equals("Artist") || this.type.equals("Artist by type") || this.type.equals("Artist by area")){
            JSONArray jsonArray = result.getJSONArray("artists");
            if(jsonArray.length() > 0){
                for(int i = 0; i < jsonArray.length(); i++){
                    String mbid = jsonArray.getJSONObject(i).get("id").toString();
                    String text = jsonArray.getJSONObject(i).get("name").toString();
                    String subtext = "";
                    if(jsonArray.getJSONObject(i).has("type")){
                        subtext = jsonArray.getJSONObject(i).get("type").toString();
                    }
                    Result result1 = new Result(mbid, text, subtext);
                    this.resultList.add(result1);
                }
                this.ui.updateList(this.resultList);
            }
            Log.d("TAG", "loadData: " + this.offset + " " + this.resultList.size());
        }
        else if(this.type.equals("Release")){
            JSONArray jsonArray = result.getJSONArray("releases");
            if(jsonArray.length() > 0){
                for(int i = 0; i < jsonArray.length(); i++){
                    String mbid = jsonArray.getJSONObject(i).get("id").toString();
                    String text = jsonArray.getJSONObject(i).get("title").toString();
                    String subtext = "";
                    if(jsonArray.getJSONObject(i).getJSONArray("artist-credit").getJSONObject(0).has("name")){
                        subtext = jsonArray.getJSONObject(i).getJSONArray("artist-credit").getJSONObject(0).get("name").toString();
                    }
                    Result result1 = new Result(mbid, text, subtext);
                    this.resultList.add(result1);
                }
                this.ui.updateList(this.resultList);
            }
            Log.d("TAG", "loadData: " + this.offset + " " + this.resultList.size());
        }
        else if(this.type.equals("Recording")){
            JSONArray jsonArray = result.getJSONArray("recordings");
            if(jsonArray.length() > 0){
                for(int i = 0; i < jsonArray.length(); i++){
                    String mbid = jsonArray.getJSONObject(i).get("id").toString();
                    Log.d(TAG, "processResult: " + mbid);
                    String text = jsonArray.getJSONObject(i).get("title").toString();
                    String subtext = jsonArray.getJSONObject(i).getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").get("name").toString();
                /*if(jsonArray.getJSONObject(i).getJSONArray("artist-credit").getJSONObject(0).has("name")){
                    subtext = jsonArray.getJSONObject(i).getJSONArray("artist-credit").getJSONObject(0).get("name").toString();
                }*/
                    Result result1 = new Result(mbid, text, subtext);
                    this.resultList.add(result1);
                }
                this.ui.updateList(this.resultList);
            }
            Log.d("TAG", "loadData: " + this.offset + " " + this.resultList.size());
        }
    }

    @Override
    public void showError(String message) {
        this.ui.showError(message);
    }


    public interface IResultFragment{
        void updateList(List<Result> list);
        void showError(String message);
    }
}
