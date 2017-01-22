package com.project.indotuber.singleton;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.project.indotuber.helpers.AnalyticsTrackers;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by yoasfs on 8/3/15.
 */
public class AppController extends Application {
    private static AppController mInstance;
    private static Context context;
    private Tracker mTracker;
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */

    private Realm realm;
    String PROJECT_NUMBER = "853512883569";
    String YOUTUBE_API = "AIzaSyAgNXbVcQpjGJzhiZhTKZYS1ZJcyoydupY";


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppController.context = getApplicationContext();
        Fabric.with(this, new Crashlytics());
        buildDatabase();
        realm = Realm.getInstance(this);
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }

    public static synchronized AppController getInstance() {
        if (mInstance == null) {
            mInstance = new AppController();
            //mInstance.theParseUser = new ParseUser();
        }
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


    public Realm getRealm() {
        return realm;
    }

    public Realm buildDatabase() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    public String getYoutubeAPI() {
        return YOUTUBE_API;
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    public String getResString(int id) {
        return getAppContext().getResources().getString(id);
    }

}
