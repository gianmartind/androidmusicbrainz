package com.example.androidmusicbrainz.artistCountry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.Artist;

import java.util.List;

public class ArtistCountryAdapter extends RecyclerView.Adapter<ArtistCountryAdapter.ViewHolder> {
    private List<Artist> list;
    private RecyclerViewClickListener clickListener;

    public ArtistCountryAdapter(List<Artist> list, RecyclerViewClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ArtistCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.artist_list_item, parent, false);

        ArtistCountryAdapter.ViewHolder viewHolder = new ArtistCountryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistCountryAdapter.ViewHolder holder, int position) {
        Artist artist = this.list.get(position);

        TextView artistName = holder.artistName;
        artistName.setText(artist.getName());

        TextView artistType = holder.artistType;
        artistType.setText(artist.getType());

        TextView artistDate = holder.artistDate;
        artistDate.setText(artist.getDate());

        TextView artistArea = holder.artistArea;
        artistArea.setText(artist.getArea());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView artistName, artistType, artistDate, artistArea;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.artistName = itemView.findViewById(R.id.artist_name);
            this.artistArea = itemView.findViewById(R.id.artist_area);
            this.artistDate = itemView.findViewById(R.id.artist_date);
            this.artistType = itemView.findViewById(R.id.artist_type);
            this.layout = itemView.findViewById(R.id.layout);
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
