package com.project.indotuber.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yoasfs on 1/22/16.
 */
public class Video {
    private String title;
    private String description;
    private String thumbnailURL;
    private String id;

    public Video(){

    }

    public Video(JSONObject object){
        try {
            setId(object.getString("id"));
            setDescription(object.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }
}
