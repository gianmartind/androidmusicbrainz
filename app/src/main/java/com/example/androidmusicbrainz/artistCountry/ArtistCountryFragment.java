package com.example.androidmusicbrainz.artistCountry;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.Artist;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ArtistCountryFragment extends Fragment implements ArtistCountryFragmentPresenter.IArtistCountryFragment, View.OnClickListener, ArtistCountryAdapter.RecyclerViewClickListener {
    FragmentListener fragmentListener;
    TextView countryTitle, errorMessage;
    RecyclerView artistList;
    ArtistCountryAdapter artistCountryAdapter;
    LinearLayout errorPage;
    ArtistCountryFragmentPresenter artistCountryFragmentPresenter;
    boolean requestSent = false;

    public ArtistCountryFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_country, container, false);

        this.countryTitle = view.findViewById(R.id.country_title);
        this.artistList = view.findViewById(R.id.artist_list);
        this.errorPage = view.findViewById(R.id.error_page);
        this.errorMessage = view.findViewById(R.id.error_message);

        this.errorPage.setOnClickListener(this);

        String mbid = this.getArguments().getString("mbid");
        this.artistCountryFragmentPresenter = new ArtistCountryFragmentPresenter(mbid, this);

        this.artistList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.artistCountryAdapter = new ArtistCountryAdapter(this.artistCountryFragmentPresenter.list, this);
        this.artistList.setAdapter(this.artistCountryAdapter);
        
        this.artistList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if(!requestSent){
                        artistCountryFragmentPresenter.loadData(getActivity());
                        requestSent = true;
                    }
                }
            }
        });

        this.countryTitle.setText("Artist by Country/Area: \n" + this.getArguments().getString("name"));

        this.artistCountryFragmentPresenter.loadData(this.getActivity());
        return view;
    }

    public static ArtistCountryFragment newInstance(String mbid, String name){
        ArtistCountryFragment fragment = new ArtistCountryFragment();

        Bundle args = new Bundle();
        args.putString("mbid", mbid);
        args.putString("name", name);
        fragment.setArguments(args);

        return fragment;
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
    public void updateList(List<Artist> list) {
        //this.artistArrayList.clear();
        //this.artistArrayList.addAll(list);
        this.requestSent = false;
        this.artistCountryAdapter.notifyItemInserted(list.size());
    }

    @Override
    public void showError(String message) {
        this.errorPage.setVisibility(View.VISIBLE);
        this.artistList.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        this.artistCountryFragmentPresenter.loadData(this.getActivity());
        this.errorPage.setVisibility(View.GONE);
        this.artistList.setVisibility(View.VISIBLE);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        this.fragmentListener.changeArtist(this.artistCountryFragmentPresenter.list.get(position).getMbid());
        this.fragmentListener.changePage(MainActivity.ARTIST);
    }


}
