package com.example.androidmusicbrainz.recordingPage;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.example.androidmusicbrainz.mainActivity.SettingsPrefSaver;
import com.example.androidmusicbrainz.model.RecordingDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class RecordingFragment extends Fragment implements View.OnClickListener, RecordingFragmentPresenter.IRecordingFragment {
    FragmentListener fragmentListener;
    ImageView recImage;
    AwesomeTextView recName, recDuration;
    TextView releaseName, artistName;
    Button searchYoutube;
    RecordingFragmentPresenter recordingFragmentPresenter;
    FloatingActionButton addToPlaylist;

    public RecordingFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recording_fragment, container, false);

        this.recImage = view.findViewById(R.id.rec_image);
        this.recName = view.findViewById(R.id.rec_name);
        this.recDuration = view.findViewById(R.id.rec_duration);
        this.releaseName = view.findViewById(R.id.release_name);
        this.artistName = view.findViewById(R.id.artist_name);
        this.searchYoutube = view.findViewById(R.id.search_youtube);
        this.addToPlaylist = view.findViewById(R.id.add_playlist);

        this.artistName.setOnClickListener(this);
        this.releaseName.setOnClickListener(this);
        this.searchYoutube.setOnClickListener(this);
        this.addToPlaylist.setOnClickListener(this);

        this.recordingFragmentPresenter = new RecordingFragmentPresenter(this.getArguments().getString("mbid"), this, this.getActivity());
        this.recordingFragmentPresenter.loadData(this.getActivity());

        return view;
    }

    public static RecordingFragment newInstance(String mbid){
        RecordingFragment fragment = new RecordingFragment();

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
    public void onClick(View view) {
        if(view == this.searchYoutube){
            this.recordingFragmentPresenter.openYoutube(this.getActivity());
        } else if(view == this.artistName){
            this.recordingFragmentPresenter.openArtist();
        } else if(view == this.releaseName){
            this.recordingFragmentPresenter.openRelease();
        } else if(view == this.addToPlaylist){
            this.recordingFragmentPresenter.addToPlaylist();
        }
    }

    @Override
    public void loadData(RecordingDetail recordingDetail) {
        this.recName.setText(recordingDetail.getName());
        this.recDuration.setText(recordingDetail.getDuration());
        this.releaseName.setText(recordingDetail.getRelease());
        this.releaseName.setPaintFlags(this.releaseName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        this.artistName.setText(recordingDetail.getArtist());
        this.artistName.setPaintFlags(this.artistName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        String image = "https://coverartarchive.org/release/" + recordingDetail.getMbid() + "/front";
        Log.d(TAG, image);
        Picasso.get().load(image).placeholder(R.drawable.ic_baseline_album_24).into(this.recImage);
    }

    @Override
    public void changePage(String mbid, int page) {
        if(page == MainActivity.RELEASE){
            this.fragmentListener.changeRelease(mbid);
            this.fragmentListener.changePage(page);
        } else if(page == MainActivity.ARTIST){
            this.fragmentListener.changeArtist(mbid);
            this.fragmentListener.changePage(page);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
