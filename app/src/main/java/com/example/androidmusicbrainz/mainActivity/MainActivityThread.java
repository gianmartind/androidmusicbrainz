package com.example.androidmusicbrainz.mainActivity;

import android.util.Log;
import android.widget.LinearLayout;

import static android.content.ContentValues.TAG;

public class MainActivityThread extends Thread {
    protected ThreadHandler threadHandler;
    protected int seconds;

    public MainActivityThread(ThreadHandler threadHandler, int seconds){
        this.threadHandler = threadHandler;
        this.seconds = seconds;
    }

    public void run(){
        while(this.seconds > 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.seconds--;
        }
        this.threadHandler.changeStatus();
    }
}
