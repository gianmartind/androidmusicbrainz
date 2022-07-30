package com.example.androidmusicbrainz.mainActivity;

import androidx.appcompat.app.AppCompatDelegate;


public class MainActivityPresenter {
    private SettingsPrefSaver settingsPrefSaver;
    private IMainActivity ui;
    private ThreadHandler threadHandler;

    public MainActivityPresenter(SettingsPrefSaver settingsPrefSaver, IMainActivity ui){
        this.settingsPrefSaver = settingsPrefSaver;
        this.ui = ui;
        this.threadHandler = new ThreadHandler(this);
    }

    public int backToPage(int page){
        switch (page){
            case MainActivity.HOME:
                return -2;
            case MainActivity.PLAYLIST:
                return -1;
            case MainActivity.SETTINGS:
                return -1;
            case MainActivity.RECORDING:
                return -1;
            case MainActivity.RELEASE:
                return -1;
            case MainActivity.ARTIST_COUNTRY:
                return -1;
            case MainActivity.ARTIST:
                return -1;
            case MainActivity.LOGIN:
                return MainActivity.HOME;
            case MainActivity.SIGNUP:
                return MainActivity.LOGIN;
            case MainActivity.SEARCH:
                return -1;
            case MainActivity.RESULT:
                return -1;
            default:
                return -1;
        }
    }

    public void openTimer(){
        MainActivityThread mainActivityThread = new MainActivityThread(this.threadHandler, 2);
        mainActivityThread.start();
    }

    public void changeStatus(){
        this.ui.changeStatus();
    }

    public void loadSettings(){
        if(this.settingsPrefSaver.getDarkMode().equals("NO")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if(this.settingsPrefSaver.getDarkMode().equals("YES")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if(this.settingsPrefSaver.getDarkMode().equals("DEF")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public interface IMainActivity{
        void changeStatus();
    }
}
