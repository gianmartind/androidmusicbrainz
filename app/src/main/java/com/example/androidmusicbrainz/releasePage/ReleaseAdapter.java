package com.example.androidmusicbrainz.releasePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.astistPage.ArtistAdapter;
import com.example.androidmusicbrainz.model.Track;

import java.util.List;

public class ReleaseAdapter extends RecyclerView.Adapter<ReleaseAdapter.ViewHolder> {
    private List<Track> list;
    private RecyclerViewClickListener clickListener;

    public ReleaseAdapter(List<Track> list, RecyclerViewClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ReleaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.track_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ReleaseAdapter.ViewHolder holder, int position) {
        Track track = list.get(position);

        TextView trackName = holder.trackName;
        trackName.setText(track.getTrackName());

        TextView subtext = holder.subtext;
        subtext.setText(track.getArtistName());

        TextView duration = holder.duration;
        duration.setText(track.getTrackDuration());
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
    

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView trackName;
        public TextView subtext;
        public TextView duration;
        public CardView layout;

        public ViewHolder(View view){
            super(view);
            this.trackName = view.findViewById(R.id.track_name);
            this.subtext = view.findViewById(R.id.artist_name);
            this.duration = view.findViewById(R.id.track_duration);
            this.layout = view.findViewById(R.id.layout);
            this.layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }
    
}
