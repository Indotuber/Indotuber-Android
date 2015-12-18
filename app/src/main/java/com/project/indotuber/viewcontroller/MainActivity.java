package com.project.indotuber.viewcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.indotuber.R;
import com.project.indotuber.singleton.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
//    Button nextButton;
//    WebView videoDisplay;
    String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = (Button)findViewById(R.id.nextButton);
        videoDisplay = (WebView)findViewById(R.id.videoWebView);
        playNextVideo();
        videoDisplay.getSettings().setJavaScriptEnabled(true);
        videoDisplay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextVideo();
            }
        });
    }


    public String playNextVideo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.getInstance().getWebsiteUrl()+"api/get-random-video";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.v("success", "success");
                    JSONObject jsonObject = new JSONObject(response);
                    videoId = jsonObject.get("videoId").toString();
                    String videoLink = "https://www.youtube.com/embed/"+videoId+"?autoplay=1";
                    String frameVideo = "<html><body> <br> <iframe width=\"340\" height=\"240\" src=\""+videoLink+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                    videoDisplay.loadData(frameVideo, "text/html", "utf-8");
                } catch (JSONException e) {
                    Log.v("failed", "failed");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("failed", "failed");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return videoId;
    }
}
