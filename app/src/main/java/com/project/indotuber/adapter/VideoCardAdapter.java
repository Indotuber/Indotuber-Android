package com.project.indotuber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.indotuber.R;

/**
 * Created by yoas on 12/18/15.
 */
public class VideoCardAdapter extends BaseAdapter {
    Context ctx;
    String videoLink;

    public VideoCardAdapter(Context ctx,String videoLink){
        this.ctx = ctx;
        this.videoLink = videoLink;
    }
    public void reloadData(){
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_videocard, parent, false);
            holder = new Holder();
            holder.videoTitle = (TextView)convertView.findViewById(R.id.item_video_title);
            holder.videoView = (WebView)convertView.findViewById(R.id.item_video_view);
            holder.videoDescription = (TextView)convertView.findViewById(R.id.item_video_description);
        }
        else {
            holder = (Holder)convertView.getTag();
        }


        return convertView;
    }

    public class Holder{
        TextView videoTitle;
        WebView videoView;
        TextView videoDescription;
    }
}
