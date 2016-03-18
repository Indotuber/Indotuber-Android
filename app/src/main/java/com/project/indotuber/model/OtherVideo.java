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

    public OtherVideo(){

    }

    public OtherVideo(JSONObject object){
        try {
            setVideoId(object.getString("videoId"));
            setVideoTitle(object.getString("videoTitle"));
            setVideoThumbnailURL("https://img.youtube.com/vi/"+object.getString("videoId")+"/1.jpg ");
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
}
