package com.project.indotuber.viewcontroller.mainView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.project.indotuber.R;
import com.project.indotuber.event.GetRandomVideoFinishEvent;
import com.project.indotuber.fonts.MontserratBoldTextView;
import com.project.indotuber.fonts.UbuntuRegulerTextView;
import com.project.indotuber.model.Video;
import com.project.indotuber.singleton.AppController;
import com.project.indotuber.singleton.ServerManager;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by yoasfs on 2/6/16.
 */
public  class MainPageFragment extends Fragment {
    Realm realm;
    Video currentVideo;

    CircleImageView creatorCircleImageView;
    MontserratBoldTextView creatorName;
    UbuntuRegulerTextView videoDescriptionTextView;
    YouTubePlayerSupportFragment youTubePlayerFragment;
    FrameLayout nextFrameLayoutButton,shareFrameLayoutButton;
    YouTubePlayer youTubePlayer;
    String shareUrl;

    public MainPageFragment(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        EventBus.getDefault().register(this);
        realm = AppController.getInstance().getRealm();
        nextFrameLayoutButton = (FrameLayout)view.findViewById(R.id.mainActivity_nextButton);
        shareFrameLayoutButton = (FrameLayout)view.findViewById(R.id.mainActivity_shareButton);
        creatorCircleImageView = (CircleImageView)view.findViewById(R.id.profile_image);
        creatorName = (MontserratBoldTextView)view.findViewById(R.id.mainActivity_creatorTextView);
        videoDescriptionTextView = (UbuntuRegulerTextView)view.findViewById(R.id.mainActivity_videoDescriptionTextView);
        // YouTubeフラグメントインスタンスを取得
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        // レイアウトにYouTubeフラグメントを追加
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mainActivity_youtubeView, youTubePlayerFragment).commit();
        // YouTubeフラグメントのプレーヤーを初期化する
        youTubePlayerFragment.initialize(AppController.getInstance().getYoutubeAPI(), new YouTubePlayer.OnInitializedListener() {

            // YouTubeプレーヤーの初期化成功
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
//                        player.loadVideo("IXh8rPZwzV4");
//                        player.play();
                }
            }

            // YouTubeプレーヤーの初期化失敗
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ServerManager.getInstance().getRandomVideo();


        nextFrameLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerManager.getInstance().getRandomVideo();
            }
        });

        shareFrameLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String message = "Nonton "
                        +currentVideo.getChannelName()
                        +" - "
                        +currentVideo.getTitle()
                        +" di "
                        +currentVideo.getVideoShareUrl();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,message );
                if (sharingIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    getActivity().startActivity(Intent.createChooser(sharingIntent, "Share using"));
                else {
                    Toast.makeText(getActivity(), "No app found on your phone which can perform this action", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initView(){

        creatorName.setText(currentVideo.getChannelName());
        if(!currentVideo.getChannelPic().equals("")) {//TODO GANTI API JADI URL
            Glide.with(getActivity()).load(currentVideo.getChannelPic()).into(creatorCircleImageView);
        }else{
            creatorCircleImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.idtuber_icon));
        }
        videoDescriptionTextView.setText(currentVideo.getDescription());
        shareUrl = currentVideo.getVideoShareUrl();
    }
    public void onEvent(GetRandomVideoFinishEvent event){
        if(event.errMessage.equals("")){
            currentVideo = event.videoResponse.getVideo();
            initView();
            youTubePlayer.loadVideo(currentVideo.getVideoId());
            youTubePlayer.play();
        }else {
            Toast.makeText(getActivity(),event.errMessage,Toast.LENGTH_LONG).show();
        }
    }
}