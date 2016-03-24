package com.project.indotuber.model;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yoasfs on 3/18/16.
 */
public class OtherVideo extends RealmObject {
    @PrimaryKey
    private String videoId;
    private String videoTitle;
    private String videoThumbnailURL;
    private String channelName;

    public OtherVideo(){

    }

    public OtherVideo(JSONObject object){
        try {
            setVideoId(object.getString("videoId"));
            setVideoTitle(object.getString("videoTitle"));
            setVideoThumbnailURL("http://img.youtube.com/vi/"+object.getString("videoId")+"/mqdefault.jpg");
            setChannelName(object.getString("channelName"));
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

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoThumbnailURL() {
        return videoThumbnailURL;
    }

    public void setVideoThumbnailURL(String videoThumbnailURL) {
        this.videoThumbnailURL = videoThumbnailURL;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
