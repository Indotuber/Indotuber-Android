package com.project.indotuber.singleton;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by yoasfs on 8/3/15.
 */
public class SettingsManager {
    private static SharedPreferences PREF=null;
    private static SharedPreferences.Editor PREF_EDITOR=null;
    private static SettingsManager SETTINGSMANAGER = null;


    public static SettingsManager getInstance() {
        if(SETTINGSMANAGER==null){
            PREF = AppController.getAppContext().getSharedPreferences("com.project.indotuber", Context.MODE_PRIVATE);
            PREF_EDITOR = PREF.edit();
            SETTINGSMANAGER = new SettingsManager();

        }
        return SETTINGSMANAGER;
    }

    public SettingsManager(){
    }

    public void setStr(String key, String value) {
        PREF_EDITOR.putString(key, value).commit();
    }
    public String getStr(String key) {
        return PREF.getString(key, "");
    }


    public String getStr(String key, String defaultValue) {
        return PREF.getString(key, defaultValue);
    }

    public boolean getBool(String key, boolean defaultValue) {
        return PREF.getBoolean(key, defaultValue);
    }

    public void setBool(String key, boolean value) {
        PREF_EDITOR.putBoolean(key, value);
        PREF_EDITOR.commit();
    }

    public int getInt(String key) {
        return PREF.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return PREF.getInt(key, defaultValue);
    }

    public void setInt(String key, int value) {
        PREF_EDITOR.putInt(key, value);
        PREF_EDITOR.commit();
    }

}
