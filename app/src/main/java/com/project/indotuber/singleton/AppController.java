package com.project.indotuber.singleton;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

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
        AppController.context = getApplicationContext();
        buildDatabase();
        realm = Realm.getInstance(this);
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
    public Realm buildDatabase(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }
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
