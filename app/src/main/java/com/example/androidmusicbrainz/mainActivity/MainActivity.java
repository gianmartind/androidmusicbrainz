package com.example.androidmusicbrainz.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.artistCountry.ArtistCountryFragment;
import com.example.androidmusicbrainz.astistPage.ArtistFragment;
import com.example.androidmusicbrainz.databinding.ActivityMainBinding;
import com.example.androidmusicbrainz.mainPage.MainFragment;
import com.example.androidmusicbrainz.playlistPage.PlaylistFragment;
import com.example.androidmusicbrainz.recordingPage.RecordingFragment;
import com.example.androidmusicbrainz.searchPage.SearchFragment;
import com.example.androidmusicbrainz.loginPage.LoginFragment;
import com.example.androidmusicbrainz.releasePage.ReleaseFragment;
import com.example.androidmusicbrainz.resultPage.ResultFragment;
import com.example.androidmusicbrainz.settingsPage.SettingsFragment;
import com.example.androidmusicbrainz.signupPage.SignupFragment;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements FragmentListener, MainActivityPresenter.IMainActivity {
    ActivityMainBinding bind;
    FragmentManager fragmentManager;
    MainActivityPresenter mainActivityPresenter;

    MainFragment mainFragment;
    ReleaseFragment releaseFragment;
    ArtistCountryFragment artistCountryFragment;
    ArtistFragment artistFragment;
    SignupFragment signupFragment;
    LoginFragment loginFragment;
    SettingsFragment settingsFragment;
    SettingsPrefSaver settingsPrefSaver;
    SearchFragment searchFragment;
    ResultFragment resultFragment;
    RecordingFragment recordingFragment;
    PlaylistFragment playlistFragment;

    int current_fragment;
    boolean can_exit;

    public static final int HOME = 0;
    public static final int PLAYLIST = 1;
    public static final int SETTINGS = 2;
    public static final int RECORDING = 3;
    public static final int RELEASE = 4;
    public static final int ARTIST_COUNTRY = 5;
    public static final int ARTIST = 6;
    public static final int LOGIN = 7;
    public static final int SIGNUP = 8;
    public static final int SEARCH = 9;
    public static final int RESULT = 10;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bind = ActivityMainBinding.inflate(getLayoutInflater());
        View view = this.bind.getRoot();
        setContentView(view);

        //Android Bootstrap
        TypefaceProvider.registerDefaultIconSets();

        this.setSupportActionBar(this.bind.toolbar);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, this.bind.drawerLayout, this.bind.toolbar, 0, 0);
        this.bind.drawerLayout.addDrawerListener(abdt);
        abdt.syncState();

        this.fragmentManager = this.getSupportFragmentManager();

        this.mainFragment = new MainFragment();
        this.signupFragment = new SignupFragment();
        this.loginFragment = new LoginFragment();
        this.releaseFragment = ReleaseFragment.newInstance("");
        this.artistCountryFragment = ArtistCountryFragment.newInstance("", "");
        this.artistFragment = ArtistFragment.newInstance("");
        this.settingsPrefSaver = new SettingsPrefSaver(this);
        this.mainActivityPresenter = new MainActivityPresenter(this.settingsPrefSaver, this);
        this.searchFragment = new SearchFragment();
        this.resultFragment = ResultFragment.newInstance("", "");
        this.recordingFragment = RecordingFragment.newInstance("");
        this.settingsFragment = new SettingsFragment();
        this.playlistFragment = new PlaylistFragment();

        this.can_exit = false;

        this.mainActivityPresenter.loadSettings();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if(networkCapabilities != null){

        } else {
            Log.d("TAG", "onCreate: " + "No internet available!");
        }
        this.changePage(HOME);
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );
        if(page == HOME){
            ft.replace(R.id.fragment_container, this.mainFragment, Integer.toString(HOME)).addToBackStack(null);
            this.current_fragment = HOME;
        } else if(page == SETTINGS){
            ft.replace(R.id.fragment_container, this.settingsFragment, Integer.toString(SETTINGS)).addToBackStack(null);
            this.current_fragment = SETTINGS;
        } else if(page == RELEASE){
            ft.replace(R.id.fragment_container, this.releaseFragment, Integer.toString(RELEASE)).addToBackStack(null);
            this.current_fragment = RELEASE;
        } else if(page == ARTIST_COUNTRY){
            ft.replace(R.id.fragment_container, this.artistCountryFragment, Integer.toString(ARTIST_COUNTRY)).addToBackStack(null);
            this.current_fragment = ARTIST_COUNTRY;
        } else if(page == ARTIST){
            ft.replace(R.id.fragment_container, this.artistFragment, Integer.toString(ARTIST)).addToBackStack(null);
            this.current_fragment = ARTIST;
        } else if(page == LOGIN){
            ft.replace(R.id.fragment_container, this.loginFragment, Integer.toString(LOGIN)).addToBackStack(null);
            this.current_fragment = LOGIN;
        } else if(page == SIGNUP){
            ft.replace(R.id.fragment_container, this.signupFragment, Integer.toString(SIGNUP)).addToBackStack(null);
            this.current_fragment = SIGNUP;
        } else if(page == SEARCH){
            ft.replace(R.id.fragment_container, this.searchFragment, Integer.toString(SEARCH)).addToBackStack(null);
            this.current_fragment = SEARCH;
        } else if(page == RESULT){
            ft.replace(R.id.fragment_container, this.resultFragment, Integer.toString(RESULT)).addToBackStack(null);
            this.current_fragment = RESULT;
        } else if(page == RECORDING){
            ft.replace(R.id.fragment_container, this.recordingFragment, Integer.toString(RECORDING)).addToBackStack(null);
            this.current_fragment = RECORDING;
        } else if(page == PLAYLIST){
            ft.replace(R.id.fragment_container, this.playlistFragment, Integer.toString(PLAYLIST)).addToBackStack(null);
            this.current_fragment = PLAYLIST;
        }
        ft.commit();

        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentById(R.id.leftFragment);
        final FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.detach(frg);
        ft2.attach(frg);
        ft2.commit();
        this.bind.drawerLayout.closeDrawers();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.bind.drawerLayout.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        long startTime = System.currentTimeMillis();
        Fragment HOME_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(HOME));
        Fragment SETTINGS_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(SETTINGS));
        Fragment RELEASE_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(RELEASE));
        Fragment ARTIST_COUNTRY_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(ARTIST_COUNTRY));
        Fragment ARTIST_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(ARTIST));
        Fragment LOGIN_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(LOGIN));
        Fragment SIGNUP_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(SIGNUP));
        Fragment SEARCH_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(SEARCH));
        Fragment RESULT_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(RESULT));
        Fragment RECORDING_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(RECORDING));
        Fragment PLAYLIST_FRAGMENT = getSupportFragmentManager().findFragmentByTag(Integer.toString(PLAYLIST));

        if(HOME_FRAGMENT != null && HOME_FRAGMENT.isVisible()){
            this.current_fragment = HOME;
        } else if(SETTINGS_FRAGMENT != null && SETTINGS_FRAGMENT.isVisible()){
            this.current_fragment = SETTINGS;
        } else if(RELEASE_FRAGMENT != null && RELEASE_FRAGMENT.isVisible()){
            this.current_fragment = RELEASE;
        } else if(ARTIST_COUNTRY_FRAGMENT != null && ARTIST_COUNTRY_FRAGMENT.isVisible()){
            this.current_fragment = ARTIST_COUNTRY;
        } else if(ARTIST_FRAGMENT != null && ARTIST_FRAGMENT.isVisible()){
            this.current_fragment = ARTIST;
        } else if(LOGIN_FRAGMENT != null && LOGIN_FRAGMENT.isVisible()){
            this.current_fragment = LOGIN;
        } else if(SIGNUP_FRAGMENT != null && SIGNUP_FRAGMENT.isVisible()){
            this.current_fragment = SIGNUP;
        } else if(SEARCH_FRAGMENT != null && SEARCH_FRAGMENT.isVisible()){
            this.current_fragment = SEARCH;
        } else if(RESULT_FRAGMENT != null && RESULT_FRAGMENT.isVisible()){
            this.current_fragment = RESULT;
        } else if(RECORDING_FRAGMENT != null && RECORDING_FRAGMENT.isVisible()){
            this.current_fragment = RECORDING;
        } else if(PLAYLIST_FRAGMENT != null && PLAYLIST_FRAGMENT.isVisible()){
            this.current_fragment = PLAYLIST;
        }

        Log.d("TAG", "onBackPressed: " + (System.currentTimeMillis() - startTime));

        if(this.mainActivityPresenter.backToPage(this.current_fragment) == -2){
            if(this.can_exit){
                this.closeApplication();
            } else {
                Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
                this.can_exit = true;
                this.mainActivityPresenter.openTimer();
            }
        } else if(this.mainActivityPresenter.backToPage(this.current_fragment) == -1){
            super.onBackPressed();
        } else {
            this.changePage(this.mainActivityPresenter.backToPage(this.current_fragment));
        }
    }

    @Override
    public void changeRelease(String mbid) {
        this.releaseFragment = ReleaseFragment.newInstance(mbid);
    }

    @Override
    public void changeArtist(String mbid) {
        this.artistFragment = ArtistFragment.newInstance(mbid);
    }

    @Override
    public void changeRecording(String mbid) {
        this.recordingFragment = RecordingFragment.newInstance(mbid);
    }

    @Override
    public void changeCountry(String mbid, String name) {
        this.artistCountryFragment = ArtistCountryFragment.newInstance(mbid, name);
    }

    @Override
    public void changeResult(String type, String query) {
        this.resultFragment = ResultFragment.newInstance(type, query);
    }


    @Override
    public void loadSettings() {
        this.mainActivityPresenter.loadSettings();
    }

    @Override
    public void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }

    @Override
    public void restartApp() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void changeStatus() {
        this.can_exit = false;
    }
    //comment
    // comment 2
    //test nanas
}
