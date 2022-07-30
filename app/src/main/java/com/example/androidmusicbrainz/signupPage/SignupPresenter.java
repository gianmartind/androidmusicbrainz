package com.example.androidmusicbrainz.signupPage;

import com.example.androidmusicbrainz.database.User;
import com.example.androidmusicbrainz.database.User_Table;
import com.example.androidmusicbrainz.mainActivity.MainActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class SignupPresenter {

    private IUserPresenter userPresenter;

    public SignupPresenter(IUserPresenter userPresenter){
        this.userPresenter = userPresenter;
    }

    public boolean checkUser(String username){
        List<User> userList = SQLite.select().from(User.class).where(User_Table.username.eq(username)).queryList();
        if(userList.size() == 0){
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

    public void signUp(String username, String password){
        if(isEmpty(username, password)){
            userPresenter.showToast("Username or Password can't be empty!");
            return;
        }
        if (checkUser(username)){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.save();
            userPresenter.showToast("Account created!");
            userPresenter.changePage(MainActivity.LOGIN);
        }
        else {
            userPresenter.showToast(username+" already exist!");
        }
    }

    public void changePage(int i){
        this.userPresenter.changePage(i);
    }

    public interface IUserPresenter{
        void changePage(int i);
        void showToast(String toast);
    }
}
