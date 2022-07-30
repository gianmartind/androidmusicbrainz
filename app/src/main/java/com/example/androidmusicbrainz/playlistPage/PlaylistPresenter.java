package com.example.androidmusicbrainz.playlistPage;

import android.util.Log;

import com.example.androidmusicbrainz.database.Playlist;
import com.example.androidmusicbrainz.database.Playlist_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PlaylistPresenter{
    protected List<Playlist> playlist;
    protected IPlaylistPage ui;
    protected String username;

    public PlaylistPresenter(IPlaylistPage view,String username){
        this.playlist = new ArrayList<>();
        this.ui = view;
        this.username = username;
    }

    public void loadData(){
        if(isLoggedIn()){
            this.getPlaylist();
            this.ui.updateList(this.playlist);
        }
        else {
            this.ui.showMessage();
        }
    }

    public void getPlaylist(){
        List<Playlist> playlist = SQLite.select().from(Playlist.class).where(Playlist_Table.username.eq(username)).queryList();
        this.playlist = playlist;
    }

    public boolean isLoggedIn(){
        if(this.username.equals("")){
            return false;
        } else {
            return true;
        }
    }

    public void deleteItem(int i){
        Playlist playlist = new Playlist();
        playlist.setId(this.playlist.get(i).getId());
        playlist.delete();
        this.getPlaylist();
        this.ui.updateList(this.playlist);
    }

    public void changePage(int id){
        this.ui.changePage(this.playlist.get(id).getMbid());
    }

    public interface IPlaylistPage{
        void updateList(List<Playlist> dataPlaylist);
        void changePage(String mbid);
        void showMessage();
    }
}
