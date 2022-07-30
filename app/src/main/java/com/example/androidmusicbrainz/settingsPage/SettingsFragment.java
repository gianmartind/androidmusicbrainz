package com.example.androidmusicbrainz.settingsPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.mainActivity.FragmentListener;
import com.example.androidmusicbrainz.mainActivity.MainActivity;


public class SettingsFragment extends Fragment implements View.OnClickListener, SettingsPagePresenter.ISettingsPage {
    RadioGroup darkMode;
    RadioButton darkYes, darkNo, darkDefault;
    BootstrapButton save, deleteAcc;
    FragmentListener fragmentListener;
    SettingsPagePresenter settingsPagePresenter;
    TextView registeredAcc;
    public SettingsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        this.darkMode = view.findViewById(R.id.dark_mode);
        this.darkYes = view.findViewById(R.id.dark_yes);
        this.darkNo = view.findViewById(R.id.dark_no);
        this.darkDefault = view.findViewById(R.id.dark_default);
        this.save = view.findViewById(R.id.save);
        this.deleteAcc = view.findViewById(R.id.delete_account);
        this.registeredAcc = view.findViewById(R.id.registered_acc);

        this.settingsPagePresenter = new SettingsPagePresenter(this, this.getActivity());
        this.save.setOnClickListener(this);
        this.deleteAcc.setOnClickListener(this);

        this.settingsPagePresenter.loadSettings();
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
    public void onClick(View view) {
        if(view == this.save){
            if(this.darkNo.isChecked()){
                this.settingsPagePresenter.saveSettings(0);
            } else if(this.darkYes.isChecked()){
                this.settingsPagePresenter.saveSettings(1);
            } else if(this.darkDefault.isChecked()){
                this.settingsPagePresenter.saveSettings(2);
            }
        } else if(view == this.deleteAcc){
            this.settingsPagePresenter.confirmDelete(this.getActivity());
        }
    }

    @Override
    public void setDarkModeButton(int i) {
        if(i == 0){
            this.darkNo.setChecked(true);
        } else if(i == 1){
            this.darkYes.setChecked(true);
        } else if(i == 2){
            this.darkDefault.setChecked(true);
        }
    }

    @Override
    public void setNumOfAccount(int i) {
        this.registeredAcc.setText("Registered Account(s): " + i);
    }

    @Override
    public void changePage() {
        this.fragmentListener.changePage(MainActivity.HOME);
    }

    @Override
    public void loadSettings() {
        this.fragmentListener.loadSettings();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
