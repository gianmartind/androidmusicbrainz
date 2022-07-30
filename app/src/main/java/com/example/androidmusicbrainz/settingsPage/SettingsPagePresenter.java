package com.example.androidmusicbrainz.settingsPage;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.androidmusicbrainz.database.Playlist;
import com.example.androidmusicbrainz.database.Playlist_Table;
import com.example.androidmusicbrainz.database.User;
import com.example.androidmusicbrainz.mainActivity.SettingsPrefSaver;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.LinkedList;
import java.util.List;

public class SettingsPagePresenter {
    public ISettingsPage ui;
    public SettingsPrefSaver settingsPrefSaver;

    public SettingsPagePresenter(ISettingsPage ui, Context context){
        this.ui = ui;
        this.settingsPrefSaver = new SettingsPrefSaver(context);
    }

    public void loadSettings(){
        List<User> users = SQLite.select().from(User.class).queryList();
        this.ui.setNumOfAccount(users.size());
        if(this.settingsPrefSaver.getDarkMode().equals("NO")){
            this.ui.setDarkModeButton(0);
        } else if(this.settingsPrefSaver.getDarkMode().equals("YES")){
            this.ui.setDarkModeButton(1);
        } else if(this.settingsPrefSaver.getDarkMode().equals("DEF")){
            this.ui.setDarkModeButton(2);
        }
    }

    public void confirmDelete(Context context){
        if(!this.settingsPrefSaver.getLogin().equals("")){
            this.ui.showToast("You need to logout first!");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Confirmation");
            builder.setMessage("Delete all accounts?\n(Including all items from playlist)");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    deleteAllAccount();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void deleteAllAccount(){
        List<User> users = SQLite.select().from(User.class).queryList();
        for(int i = 0; i < users.size(); i++){
            User user = new User();
            user.setUsername(users.get(i).getUsername());
            user.delete();
        }
        List<Playlist> playlists = SQLite.select().from(Playlist.class).queryList();
        for(int i = 0; i < playlists.size(); i++){
            Playlist playlist = new Playlist();
            playlist.setId(playlists.get(i).getId());
            playlist.delete();
        }
        this.ui.showToast("Deleted!");
        this.loadSettings();
    }

    public void saveSettings(int darkMode){
        if(darkMode == 0){
            this.settingsPrefSaver.saveDarkMode("NO");
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            this.ui.loadSettings();
            this.ui.changePage();
        } else if(darkMode == 1){
            this.settingsPrefSaver.saveDarkMode("YES");
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            this.ui.loadSettings();
            this.ui.changePage();
        } else if(darkMode == 2){
            this.settingsPrefSaver.saveDarkMode("DEF");
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            this.ui.loadSettings();
            this.ui.changePage();
        }

    }

    public interface ISettingsPage{
        void setDarkModeButton(int i);
        void setNumOfAccount(int i);
        void changePage();
        void loadSettings();
        void showToast(String message);
    }
}
