package com.project.indotuber.model;

import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yoasfs on 2/20/16.
 */
public class Channel extends RealmObject {
    @PrimaryKey
    private String channelId;

    private String channelName;
    private String channelPicUrl;
    private String indotuberChannelId;

    public Channel(){

    }

    public Channel(JSONObject object){
        try {
            setChannelId(object.getString("channelId"));
            setChannelName(object.getString("channelName"));
            setChannelPicUrl(object.getString("channelPicUrl"));
            setIndotuberChannelId(object.getString("indotuberChannelId"));
        }catch(Exception ex){

        }
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPicUrl() {
        return channelPicUrl;
    }

    public void setChannelPicUrl(String channelPicUrl) {
        this.channelPicUrl = channelPicUrl;
    }

    public String getIndotuberChannelId() {
        return indotuberChannelId;
    }

    public void setIndotuberChannelId(String indotuberChannelId) {
        this.indotuberChannelId = indotuberChannelId;
    }
}
