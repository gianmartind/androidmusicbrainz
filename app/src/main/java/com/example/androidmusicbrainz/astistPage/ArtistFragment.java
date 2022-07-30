package com.example.androidmusicbrainz.astistPage;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.Artist;
import com.example.androidmusicbrainz.model.Release;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.squareup.picasso.Picasso;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ArtistFragment extends Fragment implements ArtistFragmentPresenter.IArtistFragment, ArtistAdapter.RecyclerViewClickListener, View.OnClickListener {
    ImageView artistImage;
    TextView artistName, artistArea, artistDate, artistType;
    RecyclerView releaseList;
    ArtistFragmentPresenter artistFragmentPresenter;
    ArtistAdapter artistAdapter;

    public ArtistFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_fragment, container, false);

        this.artistImage = view.findViewById(R.id.artist_image);
        this.artistName = view.findViewById(R.id.artist_name);
        this.artistArea = view.findViewById(R.id.artist_area);
        this.artistDate = view.findViewById(R.id.artist_date);
        this.artistType = view.findViewById(R.id.artist_type);
        this.releaseList = view.findViewById(R.id.release_list);

        this.artistArea.setOnClickListener(this);

        this.artistFragmentPresenter = new ArtistFragmentPresenter(this.getArguments().getString("mbid"), this);
        this.artistFragmentPresenter.loadData(this.getActivity());

        this.releaseList.setLayoutManager(new VegaLayoutManager());
        this.artistAdapter = new ArtistAdapter(this.artistFragmentPresenter.releaseList, this);

        this.releaseList.setAdapter(this.artistAdapter);
        return view;
    }

    FragmentListener fragmentListener;


    public static ArtistFragment newInstance(String mbid){
        ArtistFragment fragment = new ArtistFragment();

        Bundle args = new Bundle();
        args.putString("mbid", mbid);
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
    public void loadData(Artist artist) {
//        if(this.releaseArrayList.size() != 0){
//            int rand = ThreadLocalRandom.current().nextInt(this.releaseArrayList.size());
//            String image = "https://coverartarchive.org/release/" + this.releaseArrayList.get(rand).getMbid() + "/front";
//            Picasso.get().load(image).placeholder(R.drawable.ic_baseline_person).into(this.artistImage);
//        } else {
//            Picasso.get().load("image/path").placeholder(R.drawable.ic_baseline_person).into(this.artistImage);
//        }
        int image = 0;
        if(artist.getType().equals("Person")){
            image = R.drawable.person;
        } else if(artist.getType().equals("Group")){
            image = R.drawable.group;
        } else if(artist.getType().equals("Choir")){
            image = R.drawable.choir;
        } else if(artist.getType().equals("Orchestra")){
            image = R.drawable.orchestra;
        } else if(artist.getType().equals("Character")){
            image = R.drawable.character;
        } else {
            image = R.drawable.other;
        }
        String imageLink = "https://logo.clearbit.com/" + artist.getName().replace(" ", "").toLowerCase() + ".com";
        Picasso.get().load(imageLink).placeholder(image).into(this.artistImage);
        this.artistName.setText(artist.getName());
        this.artistType.setText(artist.getType());
        this.artistDate.setText(artist.getDate());
        this.artistArea.setText(artist.getArea());
        this.artistArea.setPaintFlags(this.artistArea.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void loadReleases(List<Release> releaseList) {
        //this.releaseArrayList.clear();
        //this.releaseArrayList.addAll(releaseList);
        this.artistAdapter.notifyItemInserted(releaseList.size());
    }

    @Override
    public void openArtistCountry(String mbid, String name) {
        this.fragmentListener.changeCountry(mbid, name);
        this.fragmentListener.changePage(MainActivity.ARTIST_COUNTRY);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        this.fragmentListener.changeRelease(this.artistFragmentPresenter.releaseList.get(position).getMbid());
        this.fragmentListener.changePage(MainActivity.RELEASE);
    }

    @Override
    public void onClick(View view) {
        if(view == this.artistArea){
            this.artistFragmentPresenter.openArtistCountry();
        }
    }
}
