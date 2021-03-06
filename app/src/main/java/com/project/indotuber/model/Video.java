package com.project.indotuber.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yoasfs on 1/22/16.
 */
public class Video extends RealmObject {
    @PrimaryKey
    private String videoId;

    private String videoShareUrl;
    private String videoTitle;
    private String videoDescription;
    private Channel channel;
    private RealmList<OtherVideo> otherVideos;


    public Video(){

    }

    public Video(JSONObject object){
        try {
            setVideoId(object.getString("videoId"));
            setVideoShareUrl(object.getString("videoShareUrl"));
            setVideoTitle(object.getString("videoTitle"));
            setVideoDescription(object.getString("videoDescription"));
            setChannel(new Channel(object.getJSONObject("channel")));
            if(object.getJSONArray("otherVideos")!=null){
                JSONArray otherVideosJSONArray = object.getJSONArray("otherVideos");
                RealmList<OtherVideo> otherVideoRealmList = new RealmList<>();
                for(int i = 0;i<otherVideosJSONArray.length();i++){
                    JSONObject theOtherVideoJSONObject = otherVideosJSONArray.getJSONObject(i);
                    OtherVideo theOtherVideo = new OtherVideo(theOtherVideoJSONObject);
                    otherVideoRealmList.add(theOtherVideo);
                }
                setOtherVideos(otherVideoRealmList);
            }
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

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public RealmList<OtherVideo> getOtherVideos() {
        return otherVideos;
    }

    public void setOtherVideos(RealmList<OtherVideo> otherVideos) {
        this.otherVideos = otherVideos;
    }
}
