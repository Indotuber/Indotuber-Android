package com.project.indotuber.model.response;

import com.project.indotuber.model.Video;

import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yoasfs on 2/5/16.
 */
public class VideoResponse extends RealmObject {
    @PrimaryKey
    private String id;
    private Video video;

    public VideoResponse(){

    }

    public VideoResponse(JSONObject object){
        setId(0+"");
        setVideo(new Video(object));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
