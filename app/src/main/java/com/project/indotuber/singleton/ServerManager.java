package com.project.indotuber.singleton;


import android.location.Location;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.indotuber.event.GetRandomVideoFinishEvent;
import com.project.indotuber.model.Video;
import com.project.indotuber.model.response.VideoResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by yoasfs on 8/3/15.
 */
public class ServerManager {
    private static ServerManager SERVERMANAGER = null;
    boolean isSuccess = false;
//    String url = "http://10.0.1.18:8080/api/";//ko mike
    String url = "http://idtuber.com/api/";
    private RequestQueue mRequestQueue;
    public static final String TAG = ServerManager.class.getSimpleName();
    public static ServerManager getInstance() {
        if (SERVERMANAGER == null) {
            SERVERMANAGER = new ServerManager();

        }
        return SERVERMANAGER;
    }

    public ServerManager(){

    }

    public String getURL(){
        return url;
    }

    public String getErrorMessage(VolleyError error){
        String body = "Unknown Error";
        String statusCode = String.valueOf(error.networkResponse.statusCode);
        if(error.networkResponse.data!=null) {
            try {
                body = new String(error.networkResponse.data,"UTF-8");
                JSONObject jsonObject = new JSONObject(body);
                body = jsonObject.getString("message");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return body;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(AppController.getAppContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void getRandomVideo(){
        Calendar c = Calendar.getInstance();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                ServerManager.getInstance().getURL() + "get-random-video?"+c.getTimeInMillis(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                VideoResponse videoResponse = new VideoResponse(response);
                                EventBus.getDefault().post(new GetRandomVideoFinishEvent(videoResponse, ""));
                            } else {
                                EventBus.getDefault().post(new GetRandomVideoFinishEvent(null, response.getString("error")));
                            }
                        }catch (Exception e){
                            EventBus.getDefault().post(new GetRandomVideoFinishEvent(null, e.getLocalizedMessage()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new GetRandomVideoFinishEvent(null,error.getMessage()));
            }
        });
        addToRequestQueue(request,"getRandomVideo");

    }


}

