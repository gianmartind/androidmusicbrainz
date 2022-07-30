package com.example.androidmusicbrainz.leftDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidmusicbrainz.model.ItemLeftDrawer;
import com.example.androidmusicbrainz.R;

import java.util.ArrayList;
import java.util.List;

public class LeftDrawerAdapter extends BaseAdapter {
    private List<ItemLeftDrawer> listItems;
    private Context leftDrawer;

    public LeftDrawerAdapter(Context leftDrawer){
        this.leftDrawer = leftDrawer;
        this.listItems = new ArrayList<ItemLeftDrawer>();
    }

    public void addLine(ItemLeftDrawer newItem){
        this.listItems.add(newItem);
        this.notifyDataSetChanged();
    }

    public void updateList(List<ItemLeftDrawer> list){
        this.listItems = list;
    }

    @Override
    public int getCount(){
        return this.listItems.size();
    }

    @Override
    public Object getItem(int i){
        return this.listItems.get(i);
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;
        ItemLeftDrawer current = (ItemLeftDrawer) this.getItem(i);

        if(view == null){
            view = LayoutInflater.from(this.leftDrawer).inflate(R.layout.item_list_left_drawer, viewGroup,false);
            viewHolder = new ViewHolder(view);
            viewHolder.updateView(current);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

    public class ViewHolder{
        protected TextView teks_drawer_left;
        protected ImageView image;

        public ViewHolder(View view){
            this.teks_drawer_left = view.findViewById(R.id.teks_fragment_left);
            this.image = view.findViewById(R.id.img_left_drawer);
        }

        public void updateView(final ItemLeftDrawer word){
            this.teks_drawer_left.setText(word.getItem());
            this.image.setImageResource(word.getId());
        }
    }

}
