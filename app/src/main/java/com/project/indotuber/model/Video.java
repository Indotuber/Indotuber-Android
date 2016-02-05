package com.project.indotuber.model;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yoasfs on 1/22/16.
 */
public class Video extends RealmObject {
    @PrimaryKey
    private String videoId;

    private String videoShareUrl;
    private String title;
    private String description;


    private String channelId;
    private String channelTitle;
    private String channelName;
    private String channelPic;
    private String indotuberChannelId;


    public Video(){

    }

    public Video(JSONObject object){
        try {
            setVideoId(object.getString("videoId"));
            setVideoShareUrl(object.getString("videoShareUrl"));
            setTitle(object.getString("title"));
            setDescription(object.getString("description"));
            setChannelId(object.getString("channelId"));
            setChannelTitle(object.getString("channelTitle"));
            setChannelName(object.getString("channelName"));
            setChannelPic(object.getString("channelPic"));
            setIndotuberChannelId(object.getString("indotuberChannelId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoShareUrl() {
        return videoShareUrl;
    }

    public void setVideoShareUrl(String videoShareUrl) {
        this.videoShareUrl = videoShareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPic() {
        return channelPic;
    }

    public void setChannelPic(String channelPic) {
        this.channelPic = channelPic;
    }

    public String getIndotuberChannelId() {
        return indotuberChannelId;
    }

    public void setIndotuberChannelId(String indotuberChannelId) {
        this.indotuberChannelId = indotuberChannelId;
    }
}
