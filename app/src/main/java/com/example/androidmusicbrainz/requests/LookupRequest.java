package com.example.androidmusicbrainz.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LookupRequest {
    protected static final String BASE_URL = "https://musicbrainz.org/ws/2/";
    protected Context context;
    protected String entity;
    protected String inc;
    protected ILookupRequest ui;

    public LookupRequest(Context context, String entity, String inc, ILookupRequest ui) {
        this.context = context;
        this.entity = entity;
        this.inc = inc;
        this.ui = ui;
    }

    public void makeRequest(String mbid){
        String url = BASE_URL + this.entity + "/" + mbid + "?inc=" + this.inc + "&fmt=json&limit=100";
        this.callVolley(url);
    }

    public void callVolley(String url){
        url = url.replaceAll(" ", "%20");
        RequestQueue queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                try {
                    ui.processResult(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RESPONSE", "Error!");
                ui.showError(volleyError.getMessage());
            }

        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public interface ILookupRequest{
        void processResult(JSONObject result) throws JSONException;
        void showError(String message);
    }
}
