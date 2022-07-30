package com.example.androidmusicbrainz.mainPage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapWell;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.ItemGrid;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;

import org.json.JSONException;

import java.util.List;

public class MainFragment extends Fragment implements MainFragmentPresenter.IMainFragment, AdapterView.OnItemClickListener, View.OnClickListener {
    BootstrapButton search, moreCountry, refreshRelease;
    GridView artistGrid, countryGrid;
    GridAdapter artistAdapter, countryAdapter;
    MainFragmentPresenter mainFragmentPresenter;
    BootstrapWell releaseWell, countryWell;
    FragmentListener fragmentListener;
    public MainFragment(){}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        this.search = view.findViewById(R.id.search_button);
        this.artistGrid = view.findViewById(R.id.artist_grid);
        this.countryGrid = view.findViewById(R.id.country_grid);
        this.moreCountry = view.findViewById(R.id.more_country);
        this.refreshRelease = view.findViewById(R.id.refresh_release);
        this.artistAdapter = new GridAdapter(this.getActivity());
        this.countryAdapter = new GridAdapter(this.getActivity());
        this.artistGrid.setAdapter(this.artistAdapter);
        this.countryGrid.setAdapter(this.countryAdapter);

        this.artistGrid.setOnItemClickListener(this);
        this.countryGrid.setOnItemClickListener(this);

        this.releaseWell = view.findViewById(R.id.release_well);
        this.countryWell = view.findViewById(R.id.country_well);

        this.moreCountry.setOnClickListener(this);
        this.refreshRelease.setOnClickListener(this);
        this.search.setOnClickListener(this);

        this.mainFragmentPresenter = new MainFragmentPresenter(this);

        try {
            this.mainFragmentPresenter.loadData(this.getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
    public void updateArtist(List<ItemGrid> artistList) {
        this.artistAdapter.updateList(artistList);
        this.artistAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCountry(List<ItemGrid> countryList) {
        this.countryAdapter.updateList(countryList);
        this.countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void openRelease(String mbid) {
        this.fragmentListener.changeRelease(mbid);
        this.fragmentListener.changePage(MainActivity.RELEASE);
    }

    @Override
    public void openCountry(String mbid, String name) {
        this.fragmentListener.changeCountry(mbid, name);
        this.fragmentListener.changePage(MainActivity.ARTIST_COUNTRY);
    }

    @Override
    public void setView(LinearLayout.LayoutParams params) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == this.artistGrid){
            this.mainFragmentPresenter.openRelease(i);
        } else if(adapterView == this.countryGrid){
            this.mainFragmentPresenter.openCountry(i);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == this.moreCountry) {
            if(this.releaseWell.getVisibility() == View.VISIBLE){
                this.releaseWell.setVisibility(View.GONE);
                try {
                    this.mainFragmentPresenter.getExpandedCountry(this.getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                this.releaseWell.setVisibility(View.VISIBLE);
                try {
                    this.mainFragmentPresenter.getCountry(this.getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if(view == this.search) {
            this.fragmentListener.changePage(MainActivity.SEARCH);
        } else if(view == this.refreshRelease){
            try {
                this.mainFragmentPresenter.getArtists(this.getActivity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
