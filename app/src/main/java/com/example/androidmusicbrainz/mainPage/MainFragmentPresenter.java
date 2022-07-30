package com.example.androidmusicbrainz.mainPage;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.androidmusicbrainz.mainActivity.MainActivityThread;
import com.example.androidmusicbrainz.mainActivity.ThreadHandler;
import com.example.androidmusicbrainz.model.ItemGrid;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainFragmentPresenter implements GridMenuRequest.IGridRequest{
    protected List<ItemGrid> artistList;
    protected List<ItemGrid> countryList;
    protected IMainFragment ui;

    public MainFragmentPresenter(IMainFragment ui){
        this.artistList = new ArrayList<>();
        this.countryList = new ArrayList<>();
        this.ui = ui;
    }

    public void loadData(Context context) throws JSONException {
        this.getArtists(context);
        this.getCountry(context);
    }

    public void getArtists(Context context) throws JSONException {
        Random random = new Random();
        int year = random.nextInt(70) + 1950;
        int offset = random.nextInt(1000);
        GridMenuRequest gridMenuRequest = new GridMenuRequest(context, "release", this);
        gridMenuRequest.makeRequest("date:" + year + "&offset=" + offset, 6);
    }

    public void getCountry(Context context) throws JSONException {
        GridMenuRequest gridMenuRequest = new GridMenuRequest(context, "area", this);
        gridMenuRequest.makeRequest("ended:false", 12);
    }

    public void getExpandedCountry(Context context) throws JSONException {
        GridMenuRequest gridMenuRequest = new GridMenuRequest(context, "area", this);
        gridMenuRequest.makeRequest("ended:false", 100);
    }

    public void setView(LinearLayout.LayoutParams params){
        this.ui.setView(params);
    }

    @Override
    public void setArtist(ItemGrid[] itemGrids) {
        this.artistList.clear();
        this.artistList.addAll(Arrays.asList(itemGrids));
        this.ui.updateArtist(this.artistList);
    }

    @Override
    public void setCountry(ItemGrid[] itemGrids) {
        this.countryList.clear();
        this.countryList.addAll(Arrays.asList(itemGrids));
        this.ui.updateCountry(this.countryList);
    }

    public void openRelease(int i){
        this.ui.openRelease(this.artistList.get(i).getMbid());
    }

    public void openCountry(int i) {
        this.ui.openCountry(this.countryList.get(i).getMbid(), this.countryList.get(i).getName());
    }

    public interface IMainFragment{
        void updateArtist(List<ItemGrid> artistList);
        void updateCountry(List<ItemGrid> countryList);
        void openRelease(String mbid);
        void openCountry(String mbid, String name);
        void setView(LinearLayout.LayoutParams params);
    }
}
