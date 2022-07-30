package com.example.androidmusicbrainz.playlistPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.database.Playlist;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.example.androidmusicbrainz.mainActivity.SettingsPrefSaver;

import java.util.List;

public class PlaylistFragment extends Fragment implements PlaylistPresenter.IPlaylistPage, AdapterView.OnItemClickListener {
    private PlaylistAdapter playlistAdapter;
    private ListView playlistView;
    private LinearLayout need_login;
    private PlaylistPresenter playlistPresenter;
    FragmentListener fragmentListener;
    private SettingsPrefSaver settingsPrefSaver;

    public PlaylistFragment(){};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.playlist_fragment, container, false);
        this.playlistView = view.findViewById(R.id.lst_playlist);
        this.need_login = view.findViewById(R.id.need_login);
        this.settingsPrefSaver = new SettingsPrefSaver(this.getActivity());
        this.playlistPresenter = new PlaylistPresenter(this, settingsPrefSaver.getLogin());
        this.playlistAdapter = new PlaylistAdapter(this.getActivity(), this.playlistPresenter);
        this.playlistView.setAdapter(this.playlistAdapter);
        this.playlistPresenter.loadData();
        this.playlistView.setOnItemClickListener(this);
        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fragmentListener = (FragmentListener) context;
        } else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void updateList(List<Playlist> dataPlaylist) {
        playlistAdapter.updateList(dataPlaylist);
        playlistAdapter.notifyDataSetChanged();
    }

    @Override
    public void changePage(String mbid) {
        this.fragmentListener.changeRecording(mbid);
        this.fragmentListener.changePage(MainActivity.RECORDING);
    }

    @Override
    public void showMessage() {
        this.need_login.setVisibility(View.VISIBLE);
        this.playlistView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.playlistPresenter.changePage(i);
    }
}
