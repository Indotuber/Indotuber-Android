package com.project.indotuber.viewcontroller;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.project.indotuber.R;
import com.project.indotuber.singleton.AppController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.mainContainer,new MainFragment())
                .commitAllowingStateLoss();
    }

    public static class MainFragment extends Fragment{


        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);

            // YouTubeフラグメントインスタンスを取得
            YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

            // レイアウトにYouTubeフラグメントを追加
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.mainActivity_youtubeView, youTubePlayerFragment).commit();

            // YouTubeフラグメントのプレーヤーを初期化する
            youTubePlayerFragment.initialize(AppController.getInstance().getYoutubeAPI(), new OnInitializedListener() {

                // YouTubeプレーヤーの初期化成功
                @Override
                public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
                    if (!wasRestored) {
                        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        player.loadVideo("Tni74ocFxtQ");
                        player.play();
                    }
                }

                // YouTubeプレーヤーの初期化失敗
                @Override
                public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
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

        }


    }



    public void generateKeyhash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.project.indotuber",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


}
