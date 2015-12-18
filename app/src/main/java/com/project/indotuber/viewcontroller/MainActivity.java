package com.project.indotuber.viewcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.indotuber.R;
import com.project.indotuber.singleton.AppController;
import com.project.indotuber.singleton.InterfaceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button nextButton;
    WebView videoDisplay;
    String videoId = "";
    TextView titleTextView;
    TextView descriptionTextView;
    FrameLayout rootFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTextView = (TextView)findViewById(R.id.item_video_title);
        descriptionTextView = (TextView)findViewById(R.id.item_video_description);
        nextButton = (Button)findViewById(R.id.nextButton);
        videoDisplay = (WebView)findViewById(R.id.item_video_view);
        rootFrameLayout = (FrameLayout)findViewById(R.id.mainRootFrameLayout);
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
        InterfaceManager.sharedInstance().showLoading(rootFrameLayout,MainActivity.this);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.getInstance().getWebsiteUrl()+"api/get-random-video";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                InterfaceManager.sharedInstance().hideLoading();
                try {
                    Log.v("success", "success");
                    JSONObject jsonObject = new JSONObject(response);
                    videoId = jsonObject.get("videoId").toString();
                    titleTextView.setText(jsonObject.get("title").toString());
                    descriptionTextView.setText(jsonObject.get("description").toString());
                    String videoLink = "https://www.youtube.com/embed/"+videoId+"?autoplay=1";
                    String frameVideo = "<html><body> <br> <iframe width=\"300\" height=\"200\" src=\""+videoLink+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                    videoDisplay.loadData(frameVideo, "text/html", "utf-8");
                } catch (JSONException e) {
                    Log.v("failed", "failed");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                InterfaceManager.sharedInstance().hideLoading();
                Log.v("failed", "failed");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return videoId;
    }
}
