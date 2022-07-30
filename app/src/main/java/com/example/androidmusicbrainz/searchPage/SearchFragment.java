package com.example.androidmusicbrainz.searchPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;

public class SearchFragment extends Fragment implements BootstrapDropDown.OnDropDownItemClickListener, SearchFragmentPresenter.ISearchFragment, View.OnClickListener, MaterialSpinner.OnItemSelectedListener {
    FragmentListener fragmentListener;
    MaterialSpinner searchType;
    BootstrapEditText searchQuery;
    BootstrapButton searchButton;
    SearchFragmentPresenter searchFragmentPresenter;
    public SearchFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        this.searchType = view.findViewById(R.id.search_type);
        this.searchQuery = view.findViewById(R.id.search_query);
        this.searchButton = view.findViewById(R.id.search_button);

        this.searchFragmentPresenter = new SearchFragmentPresenter(this, this.getActivity());

        this.searchType.setOnItemSelectedListener(this);
        this.searchButton.setOnClickListener(this);

        this.searchFragmentPresenter.loadDropdown();
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
    public void onItemClick(ViewGroup parent, View v, int id) {
        this.searchFragmentPresenter.changeType(id);
    }

    @Override
    public void loadDropdown(String[] dropdownResource) {
        this.searchType.setItems(Arrays.asList(dropdownResource));
    }

    @Override
    public void openSearch(String query, String type) {
        this.fragmentListener.changeResult(type, query);
        this.fragmentListener.changePage(MainActivity.RESULT);
    }

    @Override
    public void onClick(View view) {
        this.searchFragmentPresenter.search(this.searchQuery.getText().toString(), this.searchType.getSelectedIndex());
        this.searchQuery.setText("");
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        this.searchFragmentPresenter.changeType(position);
    }
}
