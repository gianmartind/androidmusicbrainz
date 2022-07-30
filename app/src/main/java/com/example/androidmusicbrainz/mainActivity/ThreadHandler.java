package com.example.androidmusicbrainz.mainActivity;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

import com.example.androidmusicbrainz.mainPage.MainFragmentPresenter;

public class ThreadHandler extends Handler {
    protected final static int CHANGE_STATUS = 1;

    protected MainActivityPresenter mainActivityPresenter;

    public ThreadHandler(MainActivityPresenter mainActivityPresenter){
        this.mainActivityPresenter = mainActivityPresenter;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == CHANGE_STATUS){
            this.mainActivityPresenter.changeStatus();
        }
    }

    public void changeStatus(){
        Message msg = new Message();
        msg.what = CHANGE_STATUS;
        this.sendMessage(msg);
    }
}
