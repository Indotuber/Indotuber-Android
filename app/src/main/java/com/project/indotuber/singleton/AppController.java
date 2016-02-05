package com.project.indotuber.singleton;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by yoasfs on 8/3/15.
 */
public class AppController extends Application {
    private static AppController mInstance;
    private static Context context;
    private Realm realm;
    String PROJECT_NUMBER = "853512883569";
    String YOUTUBE_API = "AIzaSyAgNXbVcQpjGJzhiZhTKZYS1ZJcyoydupY";
    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
        realm = Realm.getInstance(this);
        AppController.context = getApplicationContext();
    }
    public static synchronized AppController getInstance() {
        if(mInstance==null){
            mInstance = new AppController();
            //mInstance.theParseUser = new ParseUser();
        }
        return mInstance;
    }

    public Realm getRealm(){
        return realm;
    }

    public String getYoutubeAPI(){
        return YOUTUBE_API;
    }
    public static Context getAppContext() {
        return AppController.context;
    }
    public String getResString(int id){
        return getAppContext().getResources().getString(id);
    }
    public String getWebsiteUrl(){
        return "http://beta.indotuber.com/";
    }

}
