package com.example.androidmusicbrainz.mainPage;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidmusicbrainz.model.CountryCode;
import com.example.androidmusicbrainz.model.ItemGrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class GridMenuRequest {
    protected static final String BASE_URL = "https://musicbrainz.org/ws/2/";
    protected Context context;
    protected String entity;
    protected IGridRequest ui;
    protected CountryCode countryCode;


    public GridMenuRequest(Context context, String entity, IGridRequest ui) {
        this.context = context;
        this.entity = entity;
        this.ui = ui;
        this.countryCode = new CountryCode();
    }

    public void makeRequest(String query, int limit) throws JSONException {
        JSONObject json = new JSONObject();
        this.callVolley(json, query, limit);
    }

    public void callVolley(JSONObject json, String query, int limit){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + this.entity + "?fmt=json" + "&query=" + query + "&limit=" + limit, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                try {
                    processResult(object.getJSONArray(entity + "s"));
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

    public void processResult(JSONArray json) throws JSONException {
        ItemGrid[] itemGrids = new ItemGrid[json.length()];

        if(this.entity.equals("release")){
            itemGrids = new ItemGrid[json.length()];
            for(int i = 0; i < json.length(); i++){
                String image = "";
                //Log.d(TAG, image);
                //int image = this.entity.equals("artist") ? R.drawable.ic_baseline_person : R.drawable.ic_baseline_landscape;
                itemGrids[i] = new ItemGrid(json.getJSONObject(i).get("id").toString(), image, json.getJSONObject(i).get("title").toString());
            }
            this.ui.setArtist(itemGrids);
        }
        else if(this.entity.equals("area")){
            for(int i = 0; i < json.length(); i++){
                String image = "";
                if(this.entity.equals("area")){
                    image = "https://www.countryflags.io/" + this.countryCode.getCountryCode(json.getJSONObject(i).get("name").toString()) + "/shiny/64.png";
                }
                itemGrids[i] = new ItemGrid(json.getJSONObject(i).get("id").toString(), image, json.getJSONObject(i).get("name").toString());
            }
            this.ui.setCountry(itemGrids);
        }
    }

    public interface IGridRequest{
        void setArtist(ItemGrid[] itemGrids);
        void setCountry(ItemGrid[] itemGrids);
    }
}
