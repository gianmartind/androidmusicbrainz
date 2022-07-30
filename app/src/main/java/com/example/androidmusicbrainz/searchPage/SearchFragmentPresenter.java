package com.example.androidmusicbrainz.searchPage;

import android.content.Context;

import com.example.androidmusicbrainz.R;

public class SearchFragmentPresenter {
    ISearchFragment ui;
    String[] dropdownResource;
    int id;

    public SearchFragmentPresenter(ISearchFragment ui, Context context){
        this.dropdownResource = context.getResources().getStringArray(R.array.dropdown_search);
        this.ui = ui;
        this.id = 0;
    }

    public void loadDropdown(){
        this.ui.loadDropdown(this.dropdownResource);
    }

    public void changeType(int id){
        this.id = id;
    }

    public void search(String query, int id){
        this.id = id;
        this.ui.openSearch(query, this.dropdownResource[this.id]);
    }

    public interface ISearchFragment{
        void loadDropdown(String[] dropdownResource);
        void openSearch(String query, String type);
    }
    //mTestArray = getResources().getStringArray(R.array.testArray);

}
