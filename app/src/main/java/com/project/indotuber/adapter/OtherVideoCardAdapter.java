package com.project.indotuber.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.indotuber.R;
import com.project.indotuber.fonts.MontserratBoldTextView;
import com.project.indotuber.fonts.UbuntuRegulerTextView;
import com.project.indotuber.model.OtherVideo;
import com.project.indotuber.singleton.ServerManager;

import io.realm.RealmList;

/**
 * Created by yoas on 12/18/15.
 */
public class OtherVideoCardAdapter extends RecyclerView.Adapter<OtherVideoCardAdapter.Holder> {
    Context ctx;
    RealmList<OtherVideo> otherVideoRealmList = new RealmList<>();
    public OtherVideoCardAdapter(Context ctx){
        this.ctx = ctx;
    }
    public void updateAdapter(RealmList<OtherVideo> otherVideos){
        this.otherVideoRealmList = otherVideos;
        notifyDataSetChanged();
    }
    public void reloadData(){
//        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_videocard, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final OtherVideo otherVideo = otherVideoRealmList.get(position);
        Glide.with(ctx).load(otherVideo.getVideoThumbnailURL()).into(holder.otherVideoThumbnail);
        holder.otherVideoTitle.setText(otherVideo.getVideoTitle());
        holder.otherVideoCreatorName.setText(otherVideo.getChannelName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerManager.getInstance().getRandomVideoByCode(otherVideo.getVideoId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return otherVideoRealmList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView otherVideoThumbnail;
        MontserratBoldTextView otherVideoCreatorName;
        UbuntuRegulerTextView otherVideoTitle;
        LinearLayout layout;
        public Holder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            layout = (LinearLayout)itemView.findViewById(R.id.otherVideoCard_layout);
            otherVideoThumbnail = (ImageView)itemView.findViewById(R.id.otherVideoCard_videoThumbnail);
            otherVideoCreatorName = (MontserratBoldTextView)itemView.findViewById(R.id.otherVideoCard_videoChannelName);
            otherVideoTitle = (UbuntuRegulerTextView)itemView.findViewById(R.id.otherVideoCard_videoTitle);
        }

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
