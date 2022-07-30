package com.example.androidmusicbrainz.astistPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.Release;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder>{

    private List<Release> list;
    private RecyclerViewClickListener clickListener;

    public ArtistAdapter(List<Release> list, RecyclerViewClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.release_list_item, parent, false);

        ArtistAdapter.ViewHolder viewHolder = new ArtistAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ViewHolder holder, int position) {
        Release release = list.get(position);

        TextView releaseName = holder.releaseName;
        releaseName.setText(release.getName());

        TextView releaseDate = holder.releaseDate;
        releaseDate.setText(release.getDate());

        CircularImageView releaseImage = holder.releaseImage;
        String image = "https://coverartarchive.org/release/" + release.getMbid() + "/front";
        Picasso.get().load(image).placeholder(R.drawable.ic_baseline_album_24).into(releaseImage);
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
        public CircularImageView releaseImage;
        public TextView releaseName;
        public TextView releaseDate;
        public CardView layout;

        public ViewHolder(View view) {
            super(view);
            this.releaseImage = view.findViewById(R.id.release_image);
            this.releaseName = view.findViewById(R.id.release_name);
            this.releaseDate = view.findViewById(R.id.release_date);
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
