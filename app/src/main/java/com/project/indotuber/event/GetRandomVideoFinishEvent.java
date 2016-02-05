package com.project.indotuber.event;

import com.project.indotuber.model.response.VideoResponse;

/**
 * Created by yoasfs on 2/5/16.
 */
public class GetRandomVideoFinishEvent {
    public VideoResponse videoResponse;
    public String errMessage;
    public GetRandomVideoFinishEvent(VideoResponse videoResponse, String errMessage){
        this.videoResponse = videoResponse;
        this.errMessage = errMessage;
    }
}
