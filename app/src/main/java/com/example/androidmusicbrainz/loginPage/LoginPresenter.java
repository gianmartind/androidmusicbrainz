package com.example.androidmusicbrainz.loginPage;

import android.content.Context;

import com.example.androidmusicbrainz.mainActivity.SettingsPrefSaver;
import com.example.androidmusicbrainz.database.User;
import com.example.androidmusicbrainz.database.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class LoginPresenter {

    private LoginPresenter.IUserPresenter userPresenter;
    private SettingsPrefSaver settingsPrefSaver;

    public LoginPresenter(LoginPresenter.IUserPresenter userPresenter, Context context){
       this.userPresenter = userPresenter;
       this.settingsPrefSaver = new SettingsPrefSaver(context);
    }

    public boolean checkUser(String username, String password){
        List<User> userList = SQLite.select().from(User.class).where(User_Table.username.eq(username)).and(User_Table.password.eq(password)).queryList();
        if(userList.size() == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEmpty(String username, String password){
        if(username.equals("") || password.equals("")){
            return true;
        } else {
            return false;
        }
    }

    public void login(String username, String password){
        if(isEmpty(username, password)){
            this.userPresenter.showToast("Empty Username or Password!");
            return;
        }
        if(checkUser(username,password)){
            this.settingsPrefSaver.saveLogin(username);
            this.userPresenter.restartApp();
        }
        else{
            userPresenter.showToast("Username or password wrong!");
        }
    }

    public void changePage(int i){
        this.userPresenter.changePage(i);
    }

    public void deleteSettingsPrefServer(){
        if(settingsPrefSaver.getLogin().equals("")){

        }
        else{
            settingsPrefSaver.saveLogin("");
            userPresenter.showToast("Successfully logged out!");
            this.userPresenter.restartApp();
        }
    }

    public interface IUserPresenter{
        void changePage(int i);
        void showToast(String toast);
        void restartApp();
    }
}
