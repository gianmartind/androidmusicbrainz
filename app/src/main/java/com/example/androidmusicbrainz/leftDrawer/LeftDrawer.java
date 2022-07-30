package com.example.androidmusicbrainz.leftDrawer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.ItemLeftDrawer;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;

import java.util.List;

public class LeftDrawer extends Fragment implements AdapterView.OnItemClickListener, LeftDrawerPresenter.ILeftDrawer{
    LeftDrawerPresenter leftDrawerPresenter;
    LeftDrawerAdapter leftDrawerAdapter;
    ListView left_fragment;
    TextView loginStatus;
    FragmentListener fragmentListener;

    public LeftDrawer(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_drawer, container, false);
        this.left_fragment = view.findViewById(R.id.list_left);
        this.leftDrawerPresenter = new LeftDrawerPresenter(this,this.getActivity());
        this.leftDrawerAdapter = new LeftDrawerAdapter(this.getActivity());
        this.left_fragment.setAdapter(this.leftDrawerAdapter);
        this.left_fragment.setOnItemClickListener(this);
        this.loginStatus = view.findViewById(R.id.login_status);
        this.leftDrawerPresenter.loadData();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i,long l){
        //home
        if(i == 0){
            this.leftDrawerPresenter.changePage(MainActivity.HOME);
        }
        //playlist
        else if(i == 1){
            this.leftDrawerPresenter.changePage(MainActivity.PLAYLIST);
        }
        //settings
        else if(i == 2){
            this.leftDrawerPresenter.changePage(MainActivity.SETTINGS);
        }
        //login
        else if(i == 3){
            this.leftDrawerPresenter.changePage(MainActivity.LOGIN);
        }
        //close app
        else if(i == 4){
            this.leftDrawerPresenter.closeApp();
        }
    }

    @Override
    public void updateList(List<ItemLeftDrawer> itemList, String login) {
        this.loginStatus.setText(login);
        this.leftDrawerAdapter.updateList(itemList);
        this.leftDrawerAdapter.notifyDataSetChanged();
    }

    @Override
    public void changePage(int i) {
        this.fragmentListener.changePage(i);
    }

    @Override
    public void closeApplication() {
        this.fragmentListener.closeApplication();
    }
}
