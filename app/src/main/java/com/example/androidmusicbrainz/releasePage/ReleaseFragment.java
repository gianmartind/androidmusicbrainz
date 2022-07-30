package com.example.androidmusicbrainz.releasePage;

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

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.example.androidmusicbrainz.model.ReleaseDetails;
import com.example.androidmusicbrainz.model.Track;
import com.squareup.picasso.Picasso;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ReleaseFragment extends Fragment implements ReleaseFragmentPresenter.IReleaseFragment, ReleaseAdapter.RecyclerViewClickListener, View.OnClickListener {
    FragmentListener fragmentListener;
    RecyclerView trackList;
    ImageView releaseImage;
    TextView releaseName, releaseArtist, releaseYear, divider;
    ReleaseFragmentPresenter releaseFragmentPresenter;
    ReleaseAdapter releaseAdapter;
    List<Track> trackArrayList;
    public ReleaseFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release_fragment, container, false);

        this.releaseImage = view.findViewById(R.id.release_image);
        this.releaseName = view.findViewById(R.id.release_name);
        this.releaseArtist = view.findViewById(R.id.release_artist);
        this.releaseYear = view.findViewById(R.id.release_year);
        this.divider = view.findViewById(R.id.divider);
        this.trackList = view.findViewById(R.id.track_list);

        this.releaseArtist.setOnClickListener(this);

        this.releaseFragmentPresenter = new ReleaseFragmentPresenter(this.getArguments().getString("mbid"), this);

        this.trackArrayList = new ArrayList<>();
        this.trackList.setLayoutManager(new VegaLayoutManager());
        this.releaseAdapter = new ReleaseAdapter(this.releaseFragmentPresenter.trackList, this);

        this.trackList.setAdapter(this.releaseAdapter);
        this.releaseFragmentPresenter.loadData(this.getActivity());
        //this.trackList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return view;
    }

    public static ReleaseFragment newInstance(String mbid){
        ReleaseFragment fragment = new ReleaseFragment();

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
    public void loadDetails(ReleaseDetails releaseDetails) {
        String image = "https://coverartarchive.org/release/" + releaseDetails.getMbid() + "/front";
        Picasso.get().load(image).placeholder(R.drawable.ic_baseline_album_24).into(this.releaseImage);
        this.releaseName.setText(releaseDetails.getTitle());
        this.releaseArtist.setText(releaseDetails.getArtist());
        this.releaseArtist.setPaintFlags(this.releaseArtist.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        this.releaseYear.setText(releaseDetails.getYear());
        this.divider.setText(" | ");
    }

    @Override
    public void loadTracks(List<Track> trackList) {
        //this.trackArrayList.clear();
        //this.trackArrayList.addAll(trackList);
        this.releaseAdapter.notifyItemInserted(trackList.size());
    }

    @Override
    public void openArtist(String mbid) {
        this.fragmentListener.changeArtist(mbid);
        this.fragmentListener.changePage(MainActivity.ARTIST);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        String mbid = this.releaseFragmentPresenter.trackList.get(position).getMbid();
        this.fragmentListener.changeRecording(mbid);
        this.fragmentListener.changePage(MainActivity.RECORDING);
    }

    @Override
    public void onClick(View view) {
        this.releaseFragmentPresenter.openArtist();
    }
}
