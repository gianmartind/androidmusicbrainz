package com.example.androidmusicbrainz.mainActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPrefSaver {
    protected SharedPreferences sharedPreferences;
    protected final static String SHARED_PREF_NAME = "com.example.androidmusicbrainz.sharedprefs";
    protected final static String KEY_DARKMODE = "DARKMODE";
    protected final static String KEY_LOGIN = "LOGIN";

    public SettingsPrefSaver(Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveDarkMode(String mode){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_DARKMODE, mode);
        editor.commit();
    }

    public String getDarkMode(){
        return this.sharedPreferences.getString(KEY_DARKMODE, "YES");
    }

    public void saveLogin(String username){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_LOGIN, username);
        editor.commit();
    }

    public String getLogin(){
        return this.sharedPreferences.getString(KEY_LOGIN, "");
    }
}
