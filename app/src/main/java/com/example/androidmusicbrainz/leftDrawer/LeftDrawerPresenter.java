package com.example.androidmusicbrainz.leftDrawer;

import android.content.Context;

import com.example.androidmusicbrainz.mainActivity.SettingsPrefSaver;
import com.example.androidmusicbrainz.model.ItemLeftDrawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeftDrawerPresenter {
    private List<ItemLeftDrawer> itemLeftDrawerList;
    private ILeftDrawer leftDrawer;
    private SettingsPrefSaver settingsPrefSaver;

    public LeftDrawerPresenter(ILeftDrawer leftDrawer, Context context){
        this.itemLeftDrawerList = new ArrayList<>();
        this.leftDrawer = leftDrawer;
        this.settingsPrefSaver = new SettingsPrefSaver(context);
    }

    public void loadData(){
        if(settingsPrefSaver.getLogin().equals("")){
            this.itemLeftDrawerList.addAll(Arrays.asList(LeftDrawerItem.array));
            this.leftDrawer.updateList(this.itemLeftDrawerList, "You're not logged in!");
        }
        else{
            this.itemLeftDrawerList.addAll(Arrays.asList(LeftDrawerItem.arrayDua));
            this.leftDrawer.updateList(this.itemLeftDrawerList, "Hello, " + this.settingsPrefSaver.getLogin() + "!");
        }
    }

    public void closeApp(){
        this.leftDrawer.closeApplication();
    }

    public void changePage(int i){
        this.leftDrawer.changePage(i);
    }

    public interface ILeftDrawer{
        void updateList(List<ItemLeftDrawer> itemList, String login);
        void changePage(int i);
        void closeApplication();
    }
}
