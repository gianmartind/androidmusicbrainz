package com.example.androidmusicbrainz.playlistPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.database.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends BaseAdapter {
    private List<Playlist> playlist;
    private Context fragment;
    private PlaylistPresenter playlistPresenter;

    public PlaylistAdapter(Context fragment, PlaylistPresenter playlistPresenter){
        this.fragment = fragment;
        this.playlist = new ArrayList<>();
        this.playlistPresenter = playlistPresenter;
    }

    @Override
    public int getCount() {
        return playlist.size();
    }

    @Override
    public Object getItem(int i) {
        return this.playlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void updateList(List<Playlist> newPlaylist){
        this.playlist = newPlaylist;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.fragment).inflate(R.layout.item_playlist_fragment, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, i);
        Playlist curr = (Playlist) this.getItem(i);
        viewHolder.updateView(curr);
        return view;
    }

    public class ViewHolder{
        protected TextView judulLagu;
        protected TextView artist;
        protected ImageButton deleteButton;
        protected int i;

        public ViewHolder(View view, int i){
            this.judulLagu = view.findViewById(R.id.judul_lagu);
            this.artist = view.findViewById(R.id.artis);
            this.deleteButton = view.findViewById(R.id.delete_item);
            this.i = i;
        }

        public void updateView(final Playlist playlist){
            this.judulLagu.setText(playlist.getJudulLagu());
            this.artist.setText(playlist.getArtist());
            this.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playlistPresenter.deleteItem(i);
                }
            });
        }
    }
}
