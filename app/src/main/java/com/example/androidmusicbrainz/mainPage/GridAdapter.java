package com.example.androidmusicbrainz.mainPage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidmusicbrainz.model.ItemGrid;
import com.example.androidmusicbrainz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GridAdapter extends BaseAdapter {
    private List<ItemGrid> list;
    private Context fragment;

    public GridAdapter(Context fragment){
        this.fragment = fragment;
        this.list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void updateList(List<ItemGrid> newList){
        this.list = newList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(this.fragment).inflate(R.layout.grid_item, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);;
        ItemGrid current = (ItemGrid) this.getItem(i);
        viewHolder.updateView(current);
        view.setTag(viewHolder);

        return view;
    }

    public class ViewHolder{
        protected ImageView image;
        protected TextView name;

        public ViewHolder(View view){
            this.image = view.findViewById(R.id.iv_pic);
            this.name = view.findViewById(R.id.tv_name);
        }

        public void updateView(final ItemGrid item){
            if(item.getImage().equals("")){
                String image = "https://coverartarchive.org/release/" + item.getMbid() + "/front";
                Picasso.get().load(image).placeholder(R.drawable.ic_baseline_album_24).fit().centerCrop().into(this.image);
            } else {
                Picasso.get().load(item.getImage()).placeholder(R.drawable.ic_baseline_map_24).into(this.image);
            }
            this.name.setText(item.getName());
        }

    }
}
