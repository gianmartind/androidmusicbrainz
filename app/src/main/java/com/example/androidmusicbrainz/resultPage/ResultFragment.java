package com.example.androidmusicbrainz.resultPage;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.example.androidmusicbrainz.model.Result;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements View.OnClickListener, ResultAdapter.RecyclerViewClickListener, ResultFragmentPresenter.IResultFragment {
    FragmentListener fragmentListener;
    TextView searchTitle;
    LinearLayout errorPage;
    RecyclerView searchList;
    ResultAdapter resultAdapter;
    ResultFragmentPresenter resultFragmentPresenter;
    boolean requestSent = false;

    public ResultFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment, container, false);
        this.searchTitle = view.findViewById(R.id.search_title);
        this.errorPage = view.findViewById(R.id.error_page);
        this.searchList = view.findViewById(R.id.result_list);

        String type = this.getArguments().getString("type");
        String query = this.getArguments().getString("query");
        this.resultFragmentPresenter = new ResultFragmentPresenter(type, query, this);
        this.searchTitle.setText(type + "\n" + "query: " + "\"" + query + "\"");

        this.searchList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.resultAdapter = new ResultAdapter(this.resultFragmentPresenter.resultList, this);
        this.searchList.setAdapter(this.resultAdapter);

        this.searchList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if(!requestSent){
                        resultFragmentPresenter.loadData(getActivity());
                        requestSent = true;
                    }
                }
            }
        });

        this.errorPage.setOnClickListener(this);

        this.resultFragmentPresenter.loadData(this.getActivity());
        return view;
    }

    public static ResultFragment newInstance(String type, String query){
        ResultFragment fragment = new ResultFragment();

        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("query", query);
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
        this.resultFragmentPresenter.loadData(this.getActivity());
        this.errorPage.setVisibility(View.GONE);
        this.searchList.setVisibility(View.VISIBLE);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        String type = this.getArguments().getString("type");
        if(type.equals("Artist") || type.equals("Artist by type") || type.equals("Artist by area")){
            this.fragmentListener.changeArtist(this.resultFragmentPresenter.resultList.get(position).getMbid());
            this.fragmentListener.changePage(MainActivity.ARTIST);
        } else if(type.equals("Release")){
            this.fragmentListener.changeRelease(this.resultFragmentPresenter.resultList.get(position).getMbid());
            this.fragmentListener.changePage(MainActivity.RELEASE);
        } else if(type.equals("Recording")){
            this.fragmentListener.changeRecording(this.resultFragmentPresenter.resultList.get(position).getMbid());
            this.fragmentListener.changePage(MainActivity.RECORDING);
        }
    }

    @Override
    public void updateList(List<Result> list) {
        //this.searchArrayList.clear();
        //this.searchArrayList.addAll(list);
        this.requestSent = false;
        this.resultAdapter.notifyItemInserted(list.size());
    }

    @Override
    public void showError(String message) {
        this.errorPage.setVisibility(View.VISIBLE);
        this.searchList.setVisibility(View.GONE);
    }

}
