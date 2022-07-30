package com.example.androidmusicbrainz.leftDrawer;

import com.example.androidmusicbrainz.R;
import com.example.androidmusicbrainz.model.ItemLeftDrawer;

public class LeftDrawerItem {
    public static ItemLeftDrawer[] array = {new ItemLeftDrawer(R.drawable.home_icon, "Home"),
            new ItemLeftDrawer(R.drawable.list_icon,"Playlist"), new ItemLeftDrawer(R.drawable.settings_icon, "Settings"),
            new ItemLeftDrawer(R.drawable.login_icon, "Login"), new ItemLeftDrawer(R.drawable.close_app_icon, "Close App")};

    public static ItemLeftDrawer[] arrayDua = {new ItemLeftDrawer(R.drawable.home_icon, "Home"),
            new ItemLeftDrawer(R.drawable.list_icon,"Playlist"), new ItemLeftDrawer(R.drawable.settings_icon, "Settings"),
            new ItemLeftDrawer(R.drawable.signout_icon, "Logout"), new ItemLeftDrawer(R.drawable.close_app_icon, "Close App")};
}
