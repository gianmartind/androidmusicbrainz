package com.example.androidmusicbrainz.releasePage;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidmusicbrainz.model.RecordingDetail;
import com.example.androidmusicbrainz.model.ReleaseDetails;
import com.example.androidmusicbrainz.model.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ReleaseDetailRequest {
    protected static final String BASE_URL = "https://musicbrainz.org/ws/2/";
    protected Context context;
    protected String type;
    protected IReleaseRequest ui;

    public ReleaseDetailRequest(String type, Context context, IReleaseRequest ui){
        this.context = context;
        this.type = type;
        this.ui = ui;
    }

    public void makeRequest(String mbid){
        if(this.type.equals("details")){
            this.callVolleyDetails(mbid);
        } else if(this.type.equals("tracks")){
            this.callVolleyTracks(mbid);
        }
    }

    public void callVolleyDetails(String mbid){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "release/" + mbid + "?inc=artists&fmt=json", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                try {
                    ui.processDetails(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RESPONSE", "Error!");
            }

        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void callVolleyTracks(String mbid){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "recording?release=" + mbid + "&fmt=json", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                try {
                    ui.processTracks(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RESPONSE", "Error!");
            }

        });
        queue.add(request);
    }

    public interface IReleaseRequest{
        void processDetails(JSONObject json) throws JSONException;
        void processTracks(JSONObject json) throws JSONException;
    }
}
